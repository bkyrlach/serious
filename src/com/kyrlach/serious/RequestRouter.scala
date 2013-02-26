package com.kyrlach.serious

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object RequestRouter {
  
  def handleGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val uri = req.getRequestURI().drop(9)
    
  }
  
  def handlePost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val uri = req.getRequestURI().drop(9)
    ServiceConfig.executeService(POST, uri, req, resp)
  }
  
  private def writeResponse[A : ResponseWriter](a: A, resp: HttpServletResponse): Unit = implicitly[ResponseWriter[A]].apply(a, resp)
  
  def error(resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html")
    resp.getWriter().write("Error")
    resp.getWriter().flush()
  }
}