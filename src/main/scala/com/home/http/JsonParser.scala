package com.home.http

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


class JsonParser {

  private implicit lazy val formats: DefaultFormats = DefaultFormats

  def fromJson[T](jsonString: String)(implicit m: Manifest[T]): T = parse(jsonString).extract[T]

  def toJson[T <: AnyRef](obj: T): String = write(obj)

}

object JsonParser extends JsonParser
