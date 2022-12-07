package repositories

import baseSpec.{BaseSpec, BaseSpecWithApplication}
import com.mongodb.client.result.DeleteResult
import models.{DataModel, UpdateResponse}
import org.mongodb.scala.result
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalamock.scalatest.MockFactory
import services.{LibraryService, MockRepositoryService, RepositoryService}
import play.api.libs.json.{JsValue, Json, OFormat}
import org.mongodb.scala.result.{DeleteResult, UpdateResult}

import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, ExecutionContext, Future}


class RepositorySpec extends BaseSpecWithApplication with MockFactory with ScalaFutures{

  val mockRepository: OverarchingDataRepository = mock[OverarchingDataRepository]
  override implicit val executionContext: ExecutionContext = app.injector.instanceOf[ExecutionContext]
  val testService = new MockRepositoryService(mockRepository)
  private val model: DataModel = DataModel(
    "abcd",
    "test name",
    "test description",
    100
  )

  private val otherModel: DataModel = DataModel(
    "efgh",
    "test name",
    "test description",
    100
  )

  "create" should {
    "create a book" in {
      (mockRepository.create(_: DataModel)).expects(*).returning(Right(Future(otherModel))).once()

      Await.result(testService.create(model), Duration(100,MILLISECONDS)) shouldBe otherModel
    }

    "return an error" in {
      (mockRepository.create(_: DataModel)).expects(*).returning(Left(new Exception("A"))).once()

      assertThrows[Exception]{Await.result(testService.create(model), Duration(100,MILLISECONDS))}
    }
  }

  "read" should {
    "return a book" in {
      (mockRepository.read(_: String)).expects(*).returning(Future(Right(model))).once()

      Await.result(testService.read(""),Duration(100,MILLISECONDS)) shouldBe model
    }

    "throw an error" in{
      (mockRepository.read(_: String)).expects(*).returning(Future(Left("No data"))).once()

      assertThrows[Exception]{Await.result(testService.read(""),Duration(100,MILLISECONDS))}
    }
  }

  "update" should{
    "return true (updated)" in {
      (mockRepository.update(_: String, _:DataModel)).expects(*,*).returning(Future(new UpdateResponse(true))).once()

      Await.result(testService.update("", model), Duration(100,MILLISECONDS)).isUpdated shouldBe true
    }

    "return false (not updated)" in {
      (mockRepository.update(_: String, _: DataModel)).expects(*, *).returning(Future(new UpdateResponse(false))).once()

      Await.result(testService.update("", model), Duration(100, MILLISECONDS)).isUpdated shouldBe false
    }
  }

  "delete" should {
    val yes = DeleteResult.acknowledged(1)
    val no = DeleteResult.unacknowledged()
    "not throw an error" in {
      (mockRepository.read(_:String)).expects(*).returning(Future(Right(model)))
      (mockRepository.delete(_: String)).expects(*).returning(Future(yes))

      //assertThrows[Exception]{testService.delete("")}
    }
    "throw an error" in {
      (mockRepository.read(_:String)).expects(*).returning(Future(Right(model)))
      (mockRepository.delete(_: String)).expects(*).returning(Future(no))
      assertThrows[Exception]{testService.delete("")}
    }
  }
}
