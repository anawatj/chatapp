package routes
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.NotUsed
import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Sink, Source}
import services.MessageService
import json._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.language.postfixOps

class WebSocketRoute(messageService: MessageService)(implicit actorSystem: ActorSystem, actorMaterializer: ActorMaterializer) {
  private def echoFlow(message:String)={
    val flow: Flow[Message, Message, _] = Flow[Message].map {
      case TextMessage.Strict(message) => TextMessage(message)
      case _ => TextMessage(s"Sorry I didn't quite get that")
    }
    flow

  }
  def route = {
    path("ws"/Segment) {
      conversation_id=>{
        val data = messageService.getMessageByConversation(conversation_id)
        val message = data.map(list=>list.map(_.data).mkString("\n"))
        onComplete(message) {
          case Success(msg:String)=>handleWebSocketMessages(echoFlow(msg))
          case Failure(err)=>complete(StatusCodes.InternalServerError.intValue,err.getMessage)
        }

      }

    }
  }






}
