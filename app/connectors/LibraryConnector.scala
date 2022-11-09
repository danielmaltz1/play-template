package connectors

import models._
import repositories._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.ws.WSClient

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future


class LibraryConnector @Inject()(ws: WSClient) {
  def get[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Response] = {
    val request = ws.url(url)
    val response = request.get()
    response.map {
      result =>
        result.json.as[Response]
    }
  }
}
