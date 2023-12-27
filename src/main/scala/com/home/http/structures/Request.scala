package com.home.http.structures

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}


case class Request(endPoint: String, methodType: RestMethods.Method = RestMethods.GET)
                  (implicit url: String, httpClient: HttpClient) {

  private val apiUri = URI.create(s"$url/$endPoint")
  private val httpRequestBuilder: HttpRequest.Builder = HttpRequest.newBuilder(apiUri)

  private val bodyHandler: HttpResponse.BodyHandler[String] = HttpResponse.BodyHandlers.ofString()
  private var bodyPublisher: HttpRequest.BodyPublisher = HttpRequest.BodyPublishers.noBody()
  private var paramsQuery: String = ""

  def withHeader(key: String, value: String): HttpRequest.Builder =
    httpRequestBuilder.header(key, value)

  def withHeaders(headers: Map[String, String]): HttpRequest.Builder = {
    val headersString: List[String] = headers.toList.flatMap { case (key, value) => List(key, value) }
    httpRequestBuilder.headers(headersString:_*)
  }

  def withParam(key: String, value: String) = {
    ???
  }

  def withBody(body: String): Request = {
    bodyPublisher = HttpRequest.BodyPublishers.ofString(body)
    this
  }

  def send(): HttpResponse[String] = {
    val response = methodType match {
      case RestMethods.GET    => sendRequestGET()
      case RestMethods.POST   => sendRequestPOST()
      case RestMethods.PUT    => sendRequestPUT()
      case RestMethods.DELETE => sendRequestDELETE()
    }
    bodyPublisher = HttpRequest.BodyPublishers.noBody()
    response
  }

  private def sendRequestGET(): HttpResponse[String] = {
    val req = httpRequestBuilder.GET().build()
    httpClient.send(req, bodyHandler)
  }

  private def sendRequestPOST(): HttpResponse[String] = {
    val req = httpRequestBuilder.POST(bodyPublisher).build()
    httpClient.send(req, bodyHandler)
  }

  private def sendRequestPUT(): HttpResponse[String] = {
    val req = httpRequestBuilder.PUT(bodyPublisher).build()
    httpClient.send(req, bodyHandler)
  }

  private def sendRequestDELETE(): HttpResponse[String] = {
    val req = httpRequestBuilder.DELETE().build()
    httpClient.send(req, bodyHandler)
  }

}
