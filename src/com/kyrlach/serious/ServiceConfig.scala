package com.kyrlach.serious

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

sealed trait RequestMethod {
  def $(s: String): Path = new Path(this, s)
}
case object POST extends RequestMethod
case object GET extends RequestMethod

class Path(val rm: RequestMethod, val path: String) {
  def $(rh: RequestHandler): Unit = ServiceConfig.registerService(this, rh)
}

abstract class ServiceConfig() {
  def load(): Unit = () //Currently a NOOP
}

object ServiceConfig {
  val serviceRegistry = new scala.collection.mutable.HashMap[Path, RequestHandler]
  
  protected[serious] def registerService[T](p: Path, rh: RequestHandler): Unit = serviceRegistry += p -> rh
  
  def executeService(method: RequestMethod, uri: String, req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    serviceRegistry.filter(_._1.rm == method).filter(_._1.path == uri).headOption.get._2(req, resp)
  }
}