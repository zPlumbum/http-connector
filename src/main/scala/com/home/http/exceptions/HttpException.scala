package com.home.http.exceptions

abstract class HttpException extends Throwable {

  override def fillInStackTrace(): Throwable = this

}

object HttpException {

  class BadStatusException(errorCode: Int) extends HttpException {
    override def toString: String = s"Error code: $errorCode"
  }

}
