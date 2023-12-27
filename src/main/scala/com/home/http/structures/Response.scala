package com.home.http.structures

import com.home.http.exceptions.HttpException

case class Response[T](content: Either[HttpException, T])
