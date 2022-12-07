package services

import models._
import org.mongodb.scala.result.DeleteResult
import repositories._

import javax.inject._
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, ExecutionContext, Future}

class RepositoryService @Inject()(repository: DataRepository)(implicit executionContext: ExecutionContext){

  def create(book: DataModel): Future[DataModel] = {
    repository.create(book) match {
      case Left(value) => throw value
      case Right(value) => value
    }
  }

  def read(id: String): Future[DataModel] = {
    repository.read(id).map {
      case Right(value) => value
      case Left(value) => throw new Exception(value)
    }
  }

  def update(id: String, book: DataModel): Future[UpdateResponse] = {
    repository.update(id, book)
  }

  def delete(id: String): Unit = {
    read(id)

    val deleteResult: Future[DeleteResult] = repository.delete(id)
    if (!Await.result(deleteResult, Duration(200,MILLISECONDS)).wasAcknowledged()) throw new Exception("Deletion failed")
  }
}
class MockRepositoryService @Inject()(repository: OverarchingDataRepository)(implicit executionContext: ExecutionContext){

  def create(book: DataModel): Future[DataModel] = {
    repository.create(book) match {
      case Left(value) => throw value
      case Right(value) => value
    }
  }

  def read(id: String): Future[DataModel] = {
    repository.read(id).map {
      case Right(value) => value
      case Left(value) => throw new Exception(value)
    }
  }

  def update(id: String, book: DataModel): Future[UpdateResponse] = {
    repository.update(id, book)
  }

  def delete(id: String): Unit = {
    read(id)

    val deleteResult: Future[DeleteResult] = repository.delete(id)
    if (!Await.result(deleteResult, Duration(100,MILLISECONDS)).wasAcknowledged()) throw new Exception("Deletion failed")
  }
}
