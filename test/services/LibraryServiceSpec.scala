package services

import baseSpec.BaseSpec
import cats.data.EitherT
import connectors.LibraryConnector
import models._
import org.scalamock.handlers.CallHandler3
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.ControllerComponents
import play.api.test.Helpers._
import play.api.test._
import play.api.libs.json.{JsArray, JsObject, JsValue, Json, OFormat}
import play.api.mvc.Results.Status
import sun.awt.image.DataBufferNative

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class LibraryServiceSpec extends BaseSpec with MockFactory with ScalaFutures with GuiceOneAppPerSuite {

  val mockConnector: LibraryConnector = mock[LibraryConnector]
  implicit val executionContext: ExecutionContext = app.injector.instanceOf[ExecutionContext]
  val testService = new LibraryService(mockConnector)

  /*val gameOfThrones: JsValue = Json.obj(
    "_id" -> "someId",
    "name" -> "A Game of Thrones",
    "description" -> "The best book!!!",
    "numSales" -> 100
  )*/

  val gameOfThrones: JsValue = Json.obj(
    "kind" -> "book",
    "id" -> "10234567",
    "etag" -> "222",
    "selfLink" -> "gameofthrones.com",
    "volumeInfo" -> "info",
    "saleInfo" -> "info",
    "accessInfo" -> "info",
    "searchInfo" -> "info"
  )

  "getGoogleBook" should {
    val url: String = "testUrl"

    "return a book" in {
      (mockConnector.get[Book](_: String)(_: OFormat[Book], _: ExecutionContext))
        .expects(url, *, *)
        .returning(EitherT.rightT(gameOfThrones.as[Book]))
        .once()

      //val returnedBook: Book = Await.result(testService.getGoogleBook(urlOverride = Some(url), search = "", term = ""), Duration(5, TimeUnit.SECONDS))
      //returnedBook shouldBe gameOfThrones.as[Book]

      whenReady(testService.getGoogleBook(urlOverride = Some(url), search = "", term = "").value) { result =>
        result.toOption shouldBe Some(gameOfThrones.as[Book])
      }
    }

    "return an error" in {
      val url: String = "testUrl"

      (mockConnector.get[Book](_: String)(_: OFormat[Book], _: ExecutionContext))
        .expects(url, *, *)
        .returning(EitherT.leftT(APIError.BadAPIResponse(500, "Could not connect")) )// How do we return an error?
        .once()

      whenReady(testService.getGoogleBook(urlOverride = Some(url), search = "", term = "").value) { result =>
        result.toOption shouldBe None
      }
    }
  }

}
