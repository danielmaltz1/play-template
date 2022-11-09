package controllers
import models._
import org.mongodb.scala.result.UpdateResult
import repositories._
import play.api.mvc._
import play.api.libs.json._

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.impl.Promise


@Singleton
class ApplicationController @Inject()(
                                       dataRepository: DataRepository,
                                       val controllerComponents: ControllerComponents
                                     )
                                     ( implicit val ec: ExecutionContext) extends BaseController {

  def index(): Action[AnyContent] = Action.async { implicit request =>
    val boooks: Future[Seq[DataModel]] = dataRepository.collection.find().toFuture()
    boooks.map(items => Json.toJson(items)).map(result => Ok(result))
  }

  def create(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[DataModel] match {
      case JsSuccess(dataModel, _) =>
        dataRepository.create(dataModel).map(_ => Created)
      case JsError(_) => Future(BadRequest)
    }
  }

  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    val booksWithCorrectId: Future[Seq[DataModel]] = dataRepository.collection.find().filter(book => book._id == id).toFuture()

    booksWithCorrectId.map(x => if (x.isEmpty) BadRequest else Ok(Json.toJson(x.head)))
  }

  def update(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[DataModel] match {
      case JsSuccess(value, _) => dataRepository.update(id, value).map(item => if (item.isUpdated) Accepted else BadRequest)

      case JsError(_) => Future(BadRequest)
    }
  }

  def delete(id:String): Action[AnyContent] = Action.async { implicit request =>
    val booksWithCorrectId: Future[Seq[DataModel]] = dataRepository.collection.find().filter(book => book._id == id).toFuture()
    dataRepository.delete(id)

    booksWithCorrectId.map(x => if (x.isEmpty) BadRequest else Accepted)
  }

}






