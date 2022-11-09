package controllers

import play.api.test.FakeRequest
import play.api.http.Status
import baseSpec.BaseSpecWithApplication
import models._
import org.mongodb.scala.FindObservable
import play.api.libs.json.{JsArray, JsObject, JsValue, Json}
import play.api.mvc.{Action, AnyContent, Result}
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Success

class ApplicationControllerSpec extends BaseSpecWithApplication {

  val TestApplicationController = new ApplicationController(
    repository,
    component
  )

  private val dataModel: DataModel = DataModel(
    "abcd",
    "test name",
    "test description",
    100
  )


  "ApplicationController .index" should {
    val result = TestApplicationController.index()(FakeRequest())

    "return Ok" in {
      beforeEach()
      status(result) shouldBe Status.OK
      afterEach()
    }

  }

  "ApplicationController .create" should {

    "create a book in the database" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      status(createdResult) shouldBe Status.CREATED
      afterEach()
    }

    "produce a bad request if invalid data" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson("badDataModel"))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      status(createdResult) shouldBe Status.BAD_REQUEST

      afterEach()
    }
  }

  "ApplicationController .read" should {

    "find a book in the database by id" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request) //wait

      //Hint: You could use status(createdResult) shouldBe Status.CREATED to check this has worked again
      status(createdResult) shouldBe Status.CREATED

      val readResult: Future[Result] = TestApplicationController.read("abcd")(FakeRequest())

      //^^ pre-written

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[JsValue] shouldBe Json.toJson(dataModel)

      afterEach()
    }


    "produce a bad request if no valid data" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      status(createdResult) shouldBe Status.CREATED
      //Hint: You could use status(createdResult) shouldBe Status.CREATED to check this has worked again

      val readResult: Future[Result] = TestApplicationController.read("abd")(FakeRequest())

      status(readResult) shouldBe Status.BAD_REQUEST

      afterEach()
    }
  }

  "ApplicationController .update()" should {

    "update a book by id" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      val updateResult: Future[Result] = TestApplicationController.update("abcd")(request)
      status(updateResult) shouldBe Status.ACCEPTED

      afterEach()
    }

    "produce a bad request if bad update data" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson("dataModel"))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      val updateResult: Future[Result] = TestApplicationController.update("abcd")(request)
      status(updateResult) shouldBe Status.BAD_REQUEST

      afterEach()
    }


    "produce a bad request if no such data to update" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      val updateResult: Future[Result] = TestApplicationController.update("cd")(request)
      status(updateResult) shouldBe Status.BAD_REQUEST

      afterEach()
    }
  }

  "ApplicationController .delete()" should {

    "delete by id" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      val deleteResult: Future[Result] = TestApplicationController.delete("abcd")(FakeRequest())

      status(deleteResult) shouldBe Status.ACCEPTED

      afterEach()
    }

    "return bad request if no such id" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      val deleteResult: Future[Result] = TestApplicationController.delete("ad")(FakeRequest())

      status(deleteResult) shouldBe Status.BAD_REQUEST


      afterEach()
    }
  }

  "beforeEach " should {
    "produce an empty collection" in {
      beforeEach()
      Await.result( repository.findAll().headOption(), Duration(5, TimeUnit.SECONDS)) shouldBe None

      afterEach()
    }

    "definitely delete everything" in {
      val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      beforeEach()
      Await.result( repository.findAll().headOption(), Duration(5, TimeUnit.SECONDS)) shouldBe None

      afterEach()
    }
  }


  override def beforeEach(): Unit = repository.deleteAll()

  override def afterEach(): Unit = repository.deleteAll()

}
