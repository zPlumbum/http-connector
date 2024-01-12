import com.home.http.HttpApiClient
import com.home.http.structures.Response

import java.net.http.HttpResponse

object Main extends App {

  val httpClient = new HttpApiClient()("https://rickandmortyapi.com/api")
//  val result: HttpResponse[String] = httpClient.get("location")
//  println("result ===> " + result.body())

//  val result = httpClient.get("location")
//  println(result)

  val result: Response[Map[String, Any]] = httpClient.get[Map[String, Any]]("location")
  println(result.content.isRight)
  println(result.content.right.get)

}
