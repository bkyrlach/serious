package com.kyrlach.serious

import javax.servlet.http.HttpServletResponse

trait ResponseWriter[A] {
  def apply(a: A, resp: HttpServletResponse): Unit
}

object ResponseWriter {
  implicit object UnitResponseWriter extends ResponseWriter[Unit] {
    override def apply(x: Unit, resp: HttpServletResponse): Unit = {
      resp.setContentType("text/html")
      resp.getWriter().write("success");
    }
  }
}