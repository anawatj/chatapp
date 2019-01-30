package services

import akka.{Done, NotUsed}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{TextMessage, WebSocketRequest}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import json.{MessageRequest, MessageResponse, MessageResponseData, MessageResponseError}
import models.{ConversationMessage, Message}
import repositories.{ConversationMessageRepository, MessageRepository}
import utils.UUIDUtils
import models.MessageType._

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

class MessageService(messageRepository:MessageRepository,conversationMessageRepository: ConversationMessageRepository,uuidUtil:UUIDUtils) {



    def sendMessage(request:MessageRequest) :Future[Either[MessageResponseError,MessageResponse]]={
        val message = Message(uuidUtil.generate(),request.`type`,request.data.getOrElse(""),request.user_id.getOrElse(""))
        val conversationMessage = ConversationMessage(request.conversation_id.getOrElse(""),message.id)

        for{
          messageRet <- messageRepository.add(message)
          conversationMessageRet <-conversationMessageRepository.add(conversationMessage)
        } yield {
          callWebSocket(message.data,conversationMessage.conversation_id)
          Right(MessageResponse(MessageResponseData(message.id,message.`type`.toString,message.data,conversationMessage.conversation_id,message.user_id),StatusCodes.Created.intValue))
        }
    }

    def callWebSocket(messageText:String,conversation_id:String) =
    {
      implicit val system = ActorSystem()
      implicit val materializer = ActorMaterializer()
      import system.dispatcher

      val host = s"ws://localhost:9000/api/v1/ws/$conversation_id"
      println(host)
      val flow: Flow[akka.http.scaladsl.model.ws.Message, akka.http.scaladsl.model.ws.Message, NotUsed] =
        Flow.fromSinkAndSource(
          Sink.foreach(println),
          Source.single(TextMessage(messageText)))

      Http().singleWebSocketRequest(
        WebSocketRequest(host),
        flow)
    }



}
