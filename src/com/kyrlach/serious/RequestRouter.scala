package com.kyrlach.serious

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object RequestRouter {
  
  def route(req: HttpServletRequest, resp: HttpServletResponse, method: String): Unit = {
    val rm = method match {
      case "GET" => GET
      case "POST" => POST
    }
    rout(req, resp, rm)
  }
  
  def rout(req: HttpServletRequest, resp: HttpServletResponse, method: RequestMethod): Unit = ServiceConfig.executeService(method, req.getRequestURI, req, resp)
  
  private def writeResponse[A : ResponseWriter](a: A, resp: HttpServletResponse): Unit = implicitly[ResponseWriter[A]].apply(a, resp)
  
  def error(resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html")
    resp.getWriter().write("Error")
    resp.getWriter().flush()
  }
}