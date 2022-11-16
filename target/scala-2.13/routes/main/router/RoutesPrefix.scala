// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/daniel.maltz/Desktop/play-template/conf/routes
// @DATE:Wed Nov 16 09:32:44 GMT 2022


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
