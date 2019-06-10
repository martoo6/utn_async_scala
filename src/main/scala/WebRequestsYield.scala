import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.Future

object WebRequestsYield extends JsonSupport {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    val apiUrl = "http://localhost:8080/"


    val locations = Seq("london", "buenos aires", "rome", "tokyo", "new york", "madrid", "seoul", "moscow")

    def searchLocation(location: String) =
      Http().singleRequest(HttpRequest(uri = Uri(s"${apiUrl}location/search/").withQuery(Query("query" -> location))))
        .andThen{case _ => println(s"Got location: $location")}
        .flatMap(x => Unmarshal(x.entity).to[Seq[LocationResp]])
        .map(_.head.woeid)

    def searchLocations = Future.sequence(locations.map(searchLocation))

    def getLocation(id: Int) =
      Http().singleRequest(HttpRequest(uri = s"${apiUrl}location/$id/"))
        .flatMap(x => Unmarshal(x.entity).to[LocationWeather])

    def getLocations(ids: Iterable[Int]) = Future.sequence(ids.map(getLocation))

    def getAvgHumidity(locations: Iterable[LocationWeather]) = {
      locations.map(location => {
        location.consolidated_weather.map(_.humidity).sum / location.consolidated_weather.size
      }).sum / locations.size
    }

    for {
      locationIds <- searchLocations
      _ = println("Got locations ids!")
      locations <- getLocations(locationIds)
      _ = println("Got locations data!")
    } yield {
      val avgHumidity = getAvgHumidity(locations)
      println(avgHumidity)
    }
  }
}