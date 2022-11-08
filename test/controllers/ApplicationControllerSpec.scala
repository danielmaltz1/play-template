package controllers

import play.api.test.FakeRequest
import play.api.http.Status
import baseSpec.BaseSpecWithApplication
import models._
import play.api.libs.json.{JsArray, JsObject, JsValue, Json}
import play.api.mvc.{Action, AnyContent, Result}
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}

import scala.concurrent.{ExecutionContext, Future}

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
  }

  "ApplicationController .read" should {

    "find a book in the database by id" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      //Hint: You could use status(createdResult) shouldBe Status.CREATED to check this has worked again

      val readResult: Future[Result] = TestApplicationController.read("abcd")(FakeRequest())

      //^^ pre-written

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[JsArray] shouldBe Json.toJson(dataModel)

      afterEach()
    }
  }

  "ApplicationController .update()" should {

    "update a book by id" in {
      beforeEach()

      /*val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      val updateResult: Action[JsValue] = TestApplicationController.update("abcd")(FakeRequest())
      status(updateResult) shouldBe Status.ACCEPTED */

      afterEach()
    }
  }

  "ApplicationController .delete()" should {

    "delete by id" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      val deleteResult: Future[Result] = TestApplicationController.delete("abcd")(FakeRequest())

      status(deleteResult) shouldBe Status.ACCEPTED

      afterEach()
    }
  }


  override def beforeEach(): Unit = repository.deleteAll()

  override def afterEach(): Unit = repository.deleteAll()

}
