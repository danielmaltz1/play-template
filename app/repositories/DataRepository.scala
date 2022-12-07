package repositories

import com.google.inject.ImplementedBy
import models.{APIError, DataModel, UpdateResponse}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.empty
import org.mongodb.scala.model._
import org.mongodb.scala.result.UpdateResult
import org.mongodb.scala.{FindObservable, result}
import play.api.libs.json.JsResult.Exception
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.{Inject, Singleton}
import scala.annotation.unused
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@ImplementedBy(classOf[DataRepository])
trait OverarchingDataRepository{
  def create(book: DataModel): Either[Throwable,Future[DataModel]]
  def read(id: String): Future[Either[String,DataModel]]
  def update(id: String, book: DataModel): Future[UpdateResponse]
  def delete(id: String): Future[result.DeleteResult]
}


@Singleton
class DataRepository @Inject()(
                                mongoComponent: MongoComponent
                              )(implicit ec: ExecutionContext) extends PlayMongoRepository[DataModel](
  collectionName = "dataModels",
  mongoComponent = mongoComponent,
  domainFormat = DataModel.formats,
  indexes = Seq(IndexModel(
    Indexes.ascending("_id")
  )),
  replaceIndexes = false
) with OverarchingDataRepository {

  def create(book: DataModel): Either[Throwable,Future[DataModel]] =
    Try(collection.insertOne(book).toFuture().map(_ => book)) match {
      case Failure(exception) => Left(exception)
      case Success(value) => Right(value)
    }

  private def byID(id: String): Bson =
    Filters.and(
      Filters.equal("_id", id)
    )

  def read(id: String): Future[Either[String,DataModel]] =
    collection.find(byID(id)).headOption flatMap {
      case Some(data) =>
        Future(Right(data))
      case None =>
        Future(Left("No data to read"))
    }

  def update(id: String, book: DataModel): Future[UpdateResponse] = {
    if (id != book._id) {Future(UpdateResponse(false))}
    else {
      val ref: Future[UpdateResult] = collection.replaceOne(
        filter = byID(id),
        replacement = book,
        options = new ReplaceOptions().upsert(true) //What happens when we set this to false?
      ).toFuture()
      ref.map(x => UpdateResponse(x.wasAcknowledged()))
    }
  }


  def delete(id: String): Future[result.DeleteResult] =
    collection.deleteOne(
      filter = byID(id)
    ).toFuture()

  def deleteAll(): Future[Unit] = collection.deleteMany(empty()).toFuture().map(_ => ()) //Hint: needed for tests

  @unused
  def findAll(): FindObservable[DataModel] = {
    collection.find()

  }

}
