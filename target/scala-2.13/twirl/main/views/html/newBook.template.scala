
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._
/*1.2*/import models.DataModel
/*2.2*/import helper._

object newBook extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[DataModel],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*4.2*/(addBook: Form[DataModel])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](_display_(/*5.2*/main("Add Book")/*5.18*/{_display_(Seq[Any](format.raw/*5.19*/("""
"""),format.raw/*6.1*/("""<div>
    """),_display_(/*7.6*/helper/*7.12*/.form(action = routes.ApplicationController.addBookForm())/*7.70*/ {_display_(Seq[Any](format.raw/*7.72*/("""
    """),_display_(/*8.6*/helper/*8.12*/.CSRF.formField),format.raw/*8.27*/("""
    """),_display_(/*9.6*/helper/*9.12*/.inputText(addBook("_id"))),format.raw/*9.38*/("""
    """),_display_(/*10.6*/helper/*10.12*/.inputText(addBook("name"))),format.raw/*10.39*/("""
    """),_display_(/*11.6*/helper/*11.12*/.inputText(addBook("description"))),format.raw/*11.46*/("""
    """),_display_(/*12.6*/helper/*12.12*/.inputText(addBook("numSales"))),format.raw/*12.43*/("""
    """),format.raw/*13.5*/("""<input class="submitButton" type="submit" value="Submit">
    """)))}),format.raw/*14.6*/("""
"""),format.raw/*15.1*/("""</div>
""")))}))
      }
    }
  }

  def render(addBook:Form[DataModel],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(addBook)(request,messages)

  def f:((Form[DataModel]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (addBook) => (request,messages) => apply(addBook)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-12-13T10:46:26.745368
                  SOURCE: /Users/daniel.maltz/Desktop/play-template/app/views/newBook.scala.html
                  HASH: 08516fe3a2be7f4ccafa3bc5a915e9a39d78c96f
                  MATRIX: 432->1|463->26|817->44|990->125|1014->141|1052->142|1079->143|1115->154|1129->160|1195->218|1234->220|1265->226|1279->232|1314->247|1345->253|1359->259|1405->285|1437->291|1452->297|1500->324|1532->330|1547->336|1602->370|1634->376|1649->382|1701->413|1733->418|1826->481|1854->482
                  LINES: 17->1|18->2|23->4|28->5|28->5|28->5|29->6|30->7|30->7|30->7|30->7|31->8|31->8|31->8|32->9|32->9|32->9|33->10|33->10|33->10|34->11|34->11|34->11|35->12|35->12|35->12|36->13|37->14|38->15
                  -- GENERATED --
              */
          