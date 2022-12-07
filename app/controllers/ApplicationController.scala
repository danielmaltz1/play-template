package controllers
import models._
import repositories._
import services._
import play.api.mvc._
import play.api.libs.json._

import java.util.concurrent.TimeUnit
import javax.inject._
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, ExecutionContext, Future}


@Singleton
class ApplicationController @Inject()(
                                       dataRepository: DataRepository,
                                       service: LibraryService,
                                       repositoryService: RepositoryService,
                                       val controllerComponents: ControllerComponents
                                     )
                                     ( implicit val ec: ExecutionContext) extends BaseController {

  def index(): Action[AnyContent] = Action.async { implicit request =>
    val books: Future[Seq[DataModel]] = dataRepository.collection.find().toFuture()
    books.map(items => Json.toJson(items)).map(result => Ok(result))
  }

  def create(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[DataModel] match {
      case JsSuccess(dataModel, _) =>
        repositoryService.create(dataModel).map(_ => Created)
      case JsError(_) => Future(BadRequest)
    }
  }

  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    //val booksWithCorrectId: Future[Seq[DataModel]] = dataRepository.collection.find().filter(book => book._id == id).toFuture()
    //booksWithCorrectId.map(x => if (x.isEmpty) BadRequest else Ok(Json.toJson(x.head)))
    val booksWithCorrectId: Future[DataModel] = repositoryService.read(id)
    //booksWithCorrectId.map { x => Future.successful(Ok(views.html.example(Some(x))))}
    Future.successful(Ok(views.html.example(Some(Await.result(booksWithCorrectId,Duration(100,MILLISECONDS))))))
  }

  def readByName(name:String): Action[AnyContent] = Action.async { implicit request =>
    val booksWithCorrectId: Future[Seq[DataModel]] = dataRepository.collection.find().filter(book => book.name == name).toFuture()

    booksWithCorrectId.map(x => if (x.isEmpty) BadRequest else Ok(Json.toJson(x.head)))
  }

  def readByCopies(test: DataModel => Boolean): Action[AnyContent] = Action.async { implicit request =>
    val booksWithCorrectId: Future[Seq[DataModel]] = dataRepository.collection.find().filter(book => test(book)).toFuture()

    booksWithCorrectId.map(x => if (x.isEmpty) BadRequest else Ok(Json.toJson(x)))
  }

  def update(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[DataModel] match {
      case JsSuccess(value, _) => repositoryService.update(id, value).map(item => if (item.isUpdated) Accepted else BadRequest)

      case JsError(_) => Future(BadRequest)
    }
  }


  def updateByField(id: String, field: String, newValue: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    /*field match {
      case "id" => Future(updateId(id, newValue))
      case "name" => Future(updateName(id, newValue))
      case "description" => Future(updateDescription(id, newValue))
      case "numSales" => Future(updateCopies(id, newValue.toInt))
    }*/

    val bookToChange: Future[DataModel] = repositoryService.read(id)
    val updatedBook: DataModel = field match {
      case "id" => Await.result(bookToChange.map(book => book.copy(newValue, book.name, book.description, book.numSales)), Duration(100, TimeUnit.MILLISECONDS))
      case "name" => Await.result(bookToChange.map(book => book.copy(book._id, newValue, book.description, book.numSales)), Duration(100,TimeUnit.MILLISECONDS))
      case "description" => Await.result(bookToChange.map(book => book.copy(book._id, book.name, newValue, book.numSales)), Duration(100,TimeUnit.MILLISECONDS))
      case "numSales" => Await.result(bookToChange.map(book => book.copy(book._id, book.name, book.description, newValue.toInt)), Duration(100,TimeUnit.MILLISECONDS))
    }
    val updateResult: UpdateResponse = Await.result(repositoryService.update(id, updatedBook), Duration(100,TimeUnit.MILLISECONDS))
    if (updateResult.isUpdated) Future(Accepted) else Future(BadRequest)

  }

  /*def updateId(id: String, newValue: String): Status = {
    val bookToChange: Future[DataModel] = dataRepository.read(id)
    val updatedBook: DataModel = Await.result( bookToChange.map(book => book.copy(newValue, book.name, book.description, book.numSales)), Duration(1,TimeUnit.SECONDS))
    val updateResult: UpdateResponse = Await.result(dataRepository.update(id, updatedBook), Duration(1,TimeUnit.SECONDS))
    if (updateResult.isUpdated) Accepted else BadRequest
  }

  def updateName(id: String, newValue: String): Status = {
    val bookToChange: Future[DataModel] = dataRepository.read(id)
    val updatedBook: DataModel = Await.result(bookToChange.map(book => book.copy(book._id, newValue, book.description, book.numSales)), Duration(1,TimeUnit.SECONDS))
    val updateResult: UpdateResponse = Await.result(dataRepository.update(id, updatedBook), Duration(1, TimeUnit.SECONDS))
    if (updateResult.isUpdated) Accepted else BadRequest
  }

  def updateDescription(id: String, newValue: String): Status = {
    val bookToChange: Future[DataModel] = dataRepository.read(id)
    val updatedBook: DataModel = Await.result(bookToChange.map(book => book.copy(book._id, book.name, newValue, book.numSales)), Duration(1,TimeUnit.SECONDS))
    val updateResult: UpdateResponse = Await.result(dataRepository.update(id, updatedBook), Duration(1, TimeUnit.SECONDS))
    if (updateResult.isUpdated) Accepted else BadRequest
  }
  def updateCopies(id: String, newValue: String): Status = {
    val bookToChange: Future[DataModel] = dataRepository.read(id)
    val updatedBook: DataModel = Await.result(bookToChange.map(book => book.copy(book._id, book.name, book.description, newValue.toInt)), Duration(1,TimeUnit.SECONDS))
    val updateResult: UpdateResponse = Await.result(dataRepository.update(id, updatedBook), Duration(1, TimeUnit.SECONDS))
    if (updateResult.isUpdated) Accepted else BadRequest
  }*/

  def delete(id:String): Action[AnyContent] = Action.async { implicit request =>
    val booksWithCorrectId: Future[DataModel] = repositoryService.read(id)
    repositoryService.delete(id)
    booksWithCorrectId.map(_ => Accepted)
  }

  def getGoogleBook(search: String, term: String): Action[AnyContent] = Action.async { implicit request =>
    service.getGoogleBook(search = search, term = term).value.map {
      case Right(book) => Ok(Json.toJson(book))
      case Left(_) => BadRequest
    }
  }

  def example(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(views.html.example(None)))
  }

}






