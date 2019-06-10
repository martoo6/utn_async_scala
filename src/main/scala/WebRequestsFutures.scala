import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol
import spray.json._
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import akka.http.scaladsl.unmarshalling.Unmarshal

case class LocationResp(woeid: Int)
case class LocationWeather(consolidated_weather :Iterable[ConsolidatedWeather])
case class ConsolidatedWeather(air_pressure: Float, humidity: Float, visibility: Float)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val locationRespFormat = jsonFormat1(LocationResp)
  implicit val consolidatedWeatherFormat = jsonFormat3(ConsolidatedWeather)
  implicit val locationWeatherFormat = jsonFormat1(LocationWeather)
}

object WebRequestsFutures extends JsonSupport {
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

    searchLocations
      .flatMap(getLocations)
      .map(getAvgHumidity)
      .andThen{case e=> println(e)}

  }
}