package com.kyrlach.serious

import javax.servlet.http.HttpServletResponse

trait ResponseWriter[A] {
  def apply(a: A, resp: HttpServletResponse): Unit
}