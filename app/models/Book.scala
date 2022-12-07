package models

import play.api.libs.json.{Json, OFormat}


//case class Book(kind: String, id: String, etag: String, selfLink: String, volumeInfo: VolumeInfo, saleInfo: SaleInfo, accessInfo: AccessInfo, searchInfo: SearchInfo)

case class Book(kind: String, id: String, etag: String, selfLink: String, volumeInfo: String, saleInfo: String, accessInfo: String, searchInfo: String)

case class VolumeInfo(title: String, subtitle: String, authors: List[String], publisher: String, publishedDate: String, description: String, industryIdentifiers: Array[Identifier], readingModes: ReadingModes, pageCount: Int, printType: String, categories: Array[String], maturityRating: String, allowAnonLogging: Option[Boolean], contentVersion: String, penelizationSummary: PanelSum, imageLinks: ImageLinks, language: String, previewLink: String, infoLink: String, canonicalVolumeLink: String)
object VolumeInfo {
  implicit val formats: OFormat[VolumeInfo] = Json.format[VolumeInfo]

}
case class Identifier(`type`: String, identifier: String)
object Identifier {
  implicit val formats: OFormat[Identifier] = Json.format[Identifier]

}
case class ReadingModes(text: Boolean, image:Boolean)
object ReadingModes {
  implicit val formats: OFormat[ReadingModes] = Json.format[ReadingModes]

}
case class PanelSum(containsEpubBubbles: Boolean, containsImageBubbles: Boolean)
object PanelSum {
  implicit val formats: OFormat[PanelSum] = Json.format[PanelSum]

}
case class ImageLinks(smallThumbnail: String, thumbnail: String)
object ImageLinks {
  implicit val formats: OFormat[ImageLinks] = Json.format[ImageLinks]

}
case class SaleInfo(country: String, saleability: String, isEbook: Boolean)
object SaleInfo {
  implicit val formats: OFormat[SaleInfo] = Json.format[SaleInfo]

}
case class AccessInfo(country: String, viewability: String, embeddable: Boolean, publicDomain: Boolean, textToSpeechPermission: String, epub: Links, pdf: Links, webreaderLink: String, accessViewStatus: String, quoteSharingAllowed: Boolean)
object AccessInfo {
  implicit val formats: OFormat[AccessInfo] = Json.format[AccessInfo]
}
case class Links(isAvailable: Boolean, acsTokenLink: Option[String])
object Links {
  implicit val formats: OFormat[Links] = Json.format[Links]

}
case class SearchInfo(textSnippet: String)
object SearchInfo {
  implicit val formats: OFormat[SearchInfo] = Json.format[SearchInfo]

}

object Book {
  implicit val formats: OFormat[Book] = Json.format[Book]
}

/*
{
      "kind": "books#volume",
      "id": "MgqTYinVcJUC",
      "etag": "LzdqSHKlp4U",
      "selfLink": "https://www.googleapis.com/books/v1/volumes/MgqTYinVcJUC",
      "volumeInfo": {
        "title": "Meeting the Communist Threat : Truman to Reagan",
        "subtitle": "Truman to Reagan",
        "authors": [
          "Thomas G. Paterson Professor of History University of Connecticut"
        ],
        "publisher": "Oxford University Press, USA",
        "publishedDate": "1988-03-24",
        "description": "In this provocative new book, the distinguished diplomatic historian Thomas G. Paterson explores why and how Americans have perceived and exaggerated the Communist threat in the last half century. Telling the story through rich analysis and substantial research in private papers, government archieves, oral histories, contemporary writings, and scholarly works, Paterson explains the origins and evolution of United States global intervention. In penetrating essays on the ideas and programs of Harry S. Truman, George F. Kennan, Dwight D. Eisenhower, John Foster Dulles, John F. Kennedy, Richard M. Nixon, Henry A. Kissisnger, and Ronald Reagan, as well as on the views of dissenters from the prevailing Cold War mentality, Paterson reveals the tenacity and momentum of American thinking about threats from abroad. Paterson offers a thorough review of postwar American attitudes toward totalitariansim, the causes of international conflict, and foreign aid, and he then demonstrates how Truman acted upon these views, launched the containment doctrine, and exercised American power in both Europe and Asia. A fresh look at Eisenhower's policy in the Middle East explains how the United States became a major player in that volatile region. Paterson also presents an incisive critique of Kennedy's foreign policy, describing an administration propelled by lessons from Truman's era, an assertive, \"can-do\" style, and a grandiose notion of America's nation-building responsibilities in the Third World. Arrogance, ignorance, and impatience, Paterson argues, combined with familiar exaggerations of Soviet capabilities and intentions, to produce a rash of crises, from the Bay of Pigs and missile crisis in Cuba to the war in Vietnam. Other chapters study the flawed record of 1970s detente, CIA covert actions and the failure of congressional oversight from the 1940s to the present, and Reagan's rewriting of the history of the Vietnam War. In the last chapter, Paterson demolishes the argument that the Vietnam War could have been won and probes the analogy between Vietnam and Central America in the 1980s. Americans did not invent the Communist threat, Paterson contends, but they have certainly exaggerated it, nurturing a trenchant anti-communism that has had a devastating effect on international relations and American institutions. An important backdrop to recent foreign policy, Meeting the Communist Threat combines extensive scholarship and perceptive analysis to provide a vivid account of Cold War policy in America.",
        "industryIdentifiers": [
          {
            "type": "ISBN_13",
            "identifier": "9780198021445"
          },
          {
            "type": "ISBN_10",
            "identifier": "0198021445"
          }
        ],
        "readingModes": {
          "text": true,
          "image": true
        },
        "pageCount": 334,
        "printType": "BOOK",
        "categories": [
          "History"
        ],
        "maturityRating": "NOT_MATURE",
        "allowAnonLogging": false,
        "contentVersion": "0.2.1.0.preview.3",
        "panelizationSummary": {
          "containsEpubBubbles": false,
          "containsImageBubbles": false
        },
        "imageLinks": {
          "smallThumbnail": "http://books.google.com/books/content?id=MgqTYinVcJUC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
          "thumbnail": "http://books.google.com/books/content?id=MgqTYinVcJUC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
        },
        "language": "en",
        "previewLink": "http://books.google.co.uk/books?id=MgqTYinVcJUC&pg=PR4&dq=9780198021445+isbn&hl=&cd=1&source=gbs_api",
        "infoLink": "http://books.google.co.uk/books?id=MgqTYinVcJUC&dq=9780198021445+isbn&hl=&source=gbs_api",
        "canonicalVolumeLink": "https://books.google.com/books/about/Meeting_the_Communist_Threat_Truman_to_R.html?hl=&id=MgqTYinVcJUC"
      },
      "saleInfo": {
        "country": "GB",
        "saleability": "NOT_FOR_SALE",
        "isEbook": false
      },
      "accessInfo": {
        "country": "GB",
        "viewability": "PARTIAL",
        "embeddable": true,
        "publicDomain": false,
        "textToSpeechPermission": "ALLOWED",
        "epub": {
          "isAvailable": true,
          "acsTokenLink": "http://books.google.co.uk/books/download/Meeting_the_Communist_Threat_Truman_to_R-sample-epub.acsm?id=MgqTYinVcJUC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api"
        },
        "pdf": {
          "isAvailable": true,
          "acsTokenLink": "http://books.google.co.uk/books/download/Meeting_the_Communist_Threat_Truman_to_R-sample-pdf.acsm?id=MgqTYinVcJUC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api"
        },
        "webReaderLink": "http://play.google.com/books/reader?id=MgqTYinVcJUC&hl=&source=gbs_api",
        "accessViewStatus": "SAMPLE",
        "quoteSharingAllowed": false
      },
      "searchInfo": {
        "textSnippet": "Includes index. 1. United States — Foreign relations — 1945- 2. Anti-Communist movements — United States — History. 3. World politics— 1945- . I. Title. E744.P3118 1988 327.73 87-20433 \u003cb\u003eISBN\u003c/b\u003e 0-19-504533-5 \u003cb\u003eISBN\u003c/b\u003e&nbsp;..."
      }
    }
 */