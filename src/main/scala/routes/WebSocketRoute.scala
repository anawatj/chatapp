package routes

import akka.NotUsed
import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.language.postfixOps

class WebSocketRoute(implicit actorSystem: ActorSystem, actorMaterializer: ActorMaterializer) {

  def route =
    path("ws"/Segment) {
      conversation_id=>{

          val echoFlow: Flow[Message, Message, _] = Flow[Message].map {
            case TextMessage.Strict(text) => TextMessage(text)
            case _ => TextMessage(s"Sorry I didn't quite get that")
          }
          handleWebSocketMessages(echoFlow)

      }

    }

}
