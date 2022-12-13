
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

object example extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[DataModel,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(model: DataModel):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](_display_(/*2.2*/main("Find Book")/*2.19*/{_display_(Seq[Any](format.raw/*2.20*/("""
"""),format.raw/*3.1*/("""<h1>Google Book Api Project</h1>

<body>

<div class="justify-content-center">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">id     </th>
            <th scope="col">name     </th>
            <th scope="col">description     </th>
            <th scope="col">number of sales     </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">"""),_display_(/*19.30*/model/*19.35*/._id),format.raw/*19.39*/("""</th>
            <td>"""),_display_(/*20.18*/model/*20.23*/.name),format.raw/*20.28*/("""</td>
            <td>"""),_display_(/*21.18*/model/*21.23*/.description),format.raw/*21.35*/("""</td>
            <td>"""),_display_(/*22.18*/model/*22.23*/.numSales),format.raw/*22.32*/("""</td>
        </tr>

        </tbody>
    </table>
</div>

</body>
""")))}))
      }
    }
  }

  def render(model:DataModel): play.twirl.api.HtmlFormat.Appendable = apply(model)

  def f:((DataModel) => play.twirl.api.HtmlFormat.Appendable) = (model) => apply(model)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-12-13T11:56:19.412962
                  SOURCE: /Users/daniel.maltz/Desktop/play-template/app/views/example.scala.html
                  HASH: dc7f4c090453fb7c4757a0a84f1ccd980ad62164
                  MATRIX: 734->1|846->21|871->38|909->39|936->40|1374->451|1388->456|1413->460|1463->483|1477->488|1503->493|1553->516|1567->521|1600->533|1650->556|1664->561|1694->570
                  LINES: 21->1|26->2|26->2|26->2|27->3|43->19|43->19|43->19|44->20|44->20|44->20|45->21|45->21|45->21|46->22|46->22|46->22
                  -- GENERATED --
              */
          