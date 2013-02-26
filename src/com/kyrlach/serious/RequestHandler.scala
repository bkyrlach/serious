package com.kyrlach.serious

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.kyrlach.serious.json.JSONReader
import scala.io.Source

abstract class RequestHandler {
  def apply(req: HttpServletRequest, resp: HttpServletResponse): Unit
}

object RequestHandler {
  implicit def f1toRH[A : ResponseWriter](f: () => A): RequestHandler = {
    new RequestHandler {
      override def apply(req: HttpServletRequest, resp: HttpServletResponse): Unit = implicitly[ResponseWriter[A]].apply(f(), resp)
    }
  }
  implicit def f2toRH[A : JSONReader, B : ResponseWriter](f: A => B): RequestHandler = {
    new RequestHandler {
      override def apply(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
        implicitly[JSONReader[A]].toObject(Source.fromInputStream(req.getInputStream).mkString("")) map { a =>
          implicitly[ResponseWriter[B]].apply(f(a), resp)
        }
      }
    }
  }
}