package controllers


import play.api.test.FakeRequest
import play.api.http.Status
import baseSpec.BaseSpecWithApplication
import models._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, Result}
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}

import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, Future}
import play.api.test.CSRFTokenHelper._

class ApplicationControllerSpec extends BaseSpecWithApplication {

  val TestApplicationController = new ApplicationController(
    repository,
    service,
    repoService,
    component
  )

  private val dataModel: DataModel = DataModel(
    "abcd",
    "test name",
    "test description",
    100
  )

  private val smallDataModel: DataModel = DataModel(
    "a",
    "small test name",
    "small test description",
    10
  )

  private val bigDataModel: DataModel = DataModel(
    "abcdefg",
    "big test name",
    "big test description",
    400
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

      assertThrows[Exception]{Await.result(TestApplicationController.read("abd")(FakeRequest()), Duration(100,MILLISECONDS))}

      //val redResult = TestApplicationController.read("abd")(FakeRequest())
      //status(redResult) shouldBe Status.BAD_REQUEST

      afterEach()
    }
  }

  "ApplicationController .readByName()" should{

    "find a book by its name" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request) //wait

      //Hint: You could use status(createdResult) shouldBe Status.CREATED to check this has worked again
      status(createdResult) shouldBe Status.CREATED

      val readResult: Future[Result] = TestApplicationController.readByName("test name")(FakeRequest())

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[JsValue] shouldBe Json.toJson(dataModel)

      afterEach()
    }
  }

  "ApplicationController .readByCopies" should{
    "return all books with more than 50 copies" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request) //wait

      val bigrequest: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(bigDataModel))
      val bigcreatedResult: Future[Result] = TestApplicationController.create()(bigrequest) //wait

      val smallrequest: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(smallDataModel))
      val smallcreatedResult: Future[Result] = TestApplicationController.create()(smallrequest) //wait

      //Hint: You could use status(createdResult) shouldBe Status.CREATED to check this has worked again
      status(createdResult) shouldBe Status.CREATED
      status(bigcreatedResult) shouldBe Status.CREATED
      status(smallcreatedResult) shouldBe Status.CREATED

      Thread.sleep(100)

      val readResult: Future[Result] = TestApplicationController.readByCopies(x => x.numSales > 50)(FakeRequest())

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[JsValue] shouldBe Json.toJson(Seq(dataModel, bigDataModel))
      afterEach()
    }
  }

  "ApplicationController .update()" should {

    "update a book by id" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED; Thread.sleep(100)

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

  "ApplicationController .updateByField" should {
    "return a DataModel with a changed name" in{
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      val updateResult: Future[Result] = TestApplicationController.updateByField("abcd","name", "testnamezzz")(request)

      status(updateResult) shouldBe Status.ACCEPTED

      afterEach()
    }

    "return a DataModel with more copies sold" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.CREATED

      val updateResult: Future[Result] = TestApplicationController.updateByField("abcd", "numSales", "103")(request)
      status(updateResult) shouldBe Status.ACCEPTED
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

      assertThrows[Exception]{Await.result(TestApplicationController.delete("ad")(FakeRequest()), Duration(100,MILLISECONDS))}


      afterEach()
    }
  }

  "addBookForm" should{
    "return ok" in {
      beforeEach()
      //val request = buildGet("/newBook/form").withBody[JsValue](Json.toJson(dataModel)).withCSRFToken
      val request = FakeRequest().withCSRFToken
      val done = TestApplicationController.addBookForm()(request)
      status(done) shouldBe Status.OK

      afterEach()
    }

    "return bad request" in {
      beforeEach()

      val request: FakeRequest[JsValue] = buildGet("/api/${dataModel._id}").withBody[JsValue](Json.toJson("dataModel"))
      val done = TestApplicationController.addBookForm()(request)
      status(done) shouldBe Status.BAD_REQUEST

      afterEach()
    }
  }

  /*"beforeEach " should {
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
      //Await.wait(500)
      Await.result( repository.findAll().headOption(), Duration(5, TimeUnit.SECONDS)) shouldBe None

      afterEach()
    }
  }*/


  override def beforeEach(): Unit = repository.deleteAll(); Thread.sleep(100)

  override def afterEach(): Unit = repository.deleteAll(); Thread.sleep(100)

}
