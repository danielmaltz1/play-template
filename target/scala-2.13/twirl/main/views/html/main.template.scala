
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[String,Option[DataModel],Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
  def apply/*7.2*/(title: String)(model: Option[DataModel])(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*8.1*/("""
"""),format.raw/*9.1*/("""<!DOCTYPE html>
<html lang="en">
    <head>
        """),format.raw/*12.62*/("""
        """),format.raw/*13.9*/("""<title>"""),_display_(/*13.17*/title),format.raw/*13.22*/("""</title>
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(/*14.70*/routes/*14.76*/.Assets.versioned("/stylesheets/main.css")),format.raw/*14.118*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*15.59*/routes/*15.65*/.Assets.versioned("images/favicon.png")),format.raw/*15.104*/("""">

    </head>
    <body>
        """),format.raw/*20.32*/("""
        """),_display_(/*21.10*/content),format.raw/*21.17*/("""

      """),format.raw/*23.7*/("""<script src=""""),_display_(/*23.21*/routes/*23.27*/.Assets.versioned("javascripts/main.js")),format.raw/*23.67*/("""" type="text/javascript"></script>
    </body>
</html>
"""))
      }
    }
  }

  def render(title:String,model:Option[DataModel],content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(model)(content)

  def f:((String) => (Option[DataModel]) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (model) => (content) => apply(title)(model)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-12-07T10:04:40.715769
                  SOURCE: /Users/daniel.maltz/Desktop/play-template/app/views/main.scala.html
                  HASH: bc5a5ee0a0b194af69b84c0791120db946a60aed
                  MATRIX: 1005->260|1155->317|1182->318|1262->423|1298->432|1333->440|1359->445|1464->523|1479->529|1543->571|1631->632|1646->638|1707->677|1770->802|1807->812|1835->819|1870->827|1911->841|1926->847|1987->887
                  LINES: 26->7|31->8|32->9|35->12|36->13|36->13|36->13|37->14|37->14|37->14|38->15|38->15|38->15|42->20|43->21|43->21|45->23|45->23|45->23|45->23
                  -- GENERATED --
              */
          