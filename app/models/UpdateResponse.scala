package models

import play.api.libs.json.{Json, OFormat}

case class UpdateResponse(
                         isUpdated: Boolean
                         )

object UpdateResponse {
  implicit val formats: OFormat[UpdateResponse] = Json.format[UpdateResponse]
}
