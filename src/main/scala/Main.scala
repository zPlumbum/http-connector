import com.home.http.HttpApiClient

import java.net.http.HttpResponse

object Main extends App {

  val httpClient = new HttpApiClient("https://rickandmortyapi.com/api")
//  val result: HttpResponse[String] = httpClient.get("location")
//  println("result ===> " + result.body())

//  val result = httpClient.get("location")
//  println(result)

  val resultT = httpClient.getT[Map[String, Any]]("location")
  println(resultT)

}
