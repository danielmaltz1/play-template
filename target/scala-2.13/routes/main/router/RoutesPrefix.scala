// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/daniel.maltz/Desktop/play-template/conf/routes
// @DATE:Tue Dec 13 11:56:57 GMT 2022


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
