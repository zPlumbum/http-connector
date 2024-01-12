package com.home.http

import com.home.http.exceptions.HttpException.BadStatusException
import com.home.http.structures.{Request, Response}

import java.net.http.{HttpClient, HttpResponse}
import scala.util.{Failure, Success, Try}


class HttpApiClient(implicit url: String, httpClient: HttpClient = HttpClient.newHttpClient()) {

  def get(endPoint: String): Response[String] = {
    val response: HttpResponse[String] = Request(endPoint).send()
    buildResponse[String](response)
  }

  def get[T](endPoint: String)(implicit m: Manifest[T]): Response[T] = {
    val response = Request(endPoint).send()
    buildResponse[T](response)
  }

  private def buildResponse[T](response: HttpResponse[String])(implicit m: Manifest[T]): Response[T] = {
    val result = response.statusCode() match {
      case status if isValid(status) =>
        Try(JsonParser.fromJson[T](response.body())) match {
          case Success(value) => Right(value)
          case Failure(_) => Right(response.body().asInstanceOf[T])
        }
      case badStatus =>
        Left(new BadStatusException(badStatus))
    }
    Response(result)
  }

  private def isValid(status: Int): Boolean = if (status >= 200 && status < 300) true else false

}
