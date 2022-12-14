package services


import cats.data.EitherT
import connectors.LibraryConnector
import models._

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future


class LibraryService @Inject()(connector: LibraryConnector) {

  def getGoogleBook(urlOverride: Option[String] = None, search: String, term: String)(implicit ec: ExecutionContext): EitherT[Future, APIError, Book] =
    connector.get[Book](urlOverride.getOrElse(s"https://www.googleapis.com/books/v1/volumes?q=$search%$term"))

}

object LibraryService{}
