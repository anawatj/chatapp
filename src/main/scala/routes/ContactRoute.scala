package routes


import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import json._
import json.JsonFormat._

class ContactRoute(implicit actorSystem: ActorSystem, actorMaterializer: ActorMaterializer) {


  def route: Route = {
    pathPrefix("contacts"){
      pathEnd{
        get {
          complete {
            "Hello"
          }

        } ~
        post{
          entity(as[ContactRequest]){
            request=>complete{
              "hello"
            }
          }
        }
      } ~ path(Segment){
          name=>get{
            complete{
              "Hello"+name
            }
          }
        }

    }

  }
}
