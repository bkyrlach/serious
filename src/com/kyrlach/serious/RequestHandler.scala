package com.kyrlach.serious

import scala.io.Source

import org.w3c.dom.Document

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.parsers.DocumentBuilderFactory

class RequestHandler[A : ResponseWriter](rh: HttpServletRequest => A) {
  def apply(req: HttpServletRequest, resp: HttpServletResponse): Unit = implicitly[ResponseWriter[A]].apply(rh(req), resp)
}

object RequestHandler {
  def requestBody(implicit request: HttpServletRequest): String = Source.fromInputStream(request.getInputStream).mkString("")
  def document(path: String): Document = {
    val factory = DocumentBuilderFactory.newInstance
    val builder = factory.newDocumentBuilder
    builder.parse(this.getClass().getResourceAsStream(path))
  }
  
  def parameter(name: String)(implicit req: HttpServletRequest): Option[String] = Option(req.getParameter(name))
  def parameters(name: String)(implicit req: HttpServletRequest): Option[List[String]] = Option(req.getParameterValues(name)).map(_.toList)
}