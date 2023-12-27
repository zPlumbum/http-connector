package com.home.http

import com.home.http.exceptions.HttpException.BadStatusException
import com.home.http.structures.{Request, Response}

import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.URI
import scala.util.{Failure, Success, Try}


class HttpApiClient(url: String)(implicit httpClient: HttpClient = HttpClient.newHttpClient()) {

  private implicit val urlIml: String = url

//  def get(endPoint: String): HttpResponse[String] = {
//    val apiUrl = s"$url/$endPoint"
//    val request = HttpRequest.newBuilder().GET().uri(URI.create(apiUrl)).build()
//    httpClient.send(request, HttpResponse.BodyHandlers.ofString())
//  }

  def get(endPoint: String): Response[String] = {
    val request: Request = Request(endPoint)
    val response: HttpResponse[String] = request.send()
    buildResponse[String](response)
  }

  def getT[T](endPoint: String)(implicit m: Manifest[T]): Response[T] = {
    val request = Request(endPoint)
    val response = request.withBody("").send()
    buildResponse[T](response)
  }

  private def buildResponse[T](response: HttpResponse[String])(implicit m: Manifest[T]): Response[T] = {
    val result = response.statusCode() match {
      case status if isValid(status) =>
        Try(JsonParser.fromJson[T](response.body())) match {
          case Success(value) => Right(value)
          case Failure(_) => Right(response.body().asInstanceOf[T])
        }
      case badStatus@_ =>
        Left(new BadStatusException(badStatus))
    }
    Response(result)
  }

  private def isValid(status: Int): Boolean = if (status >= 200 && status < 300) true else false

}
