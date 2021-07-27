package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import json._
import json.JsonFormat._
import services.{ContactService, ConversationService}

import scala.util.{Failure, Success}


class ConversationRoute(conversationService: ConversationService)(implicit actorSystem: ActorSystem, actorMaterializer: ActorMaterializer) {


    def route: Route  = {
        pathPrefix("conversations"){
          pathEnd{
            post{
              entity(as[ConversationRequest]){
                request=>{
                  onComplete(conversationService.createConversation(request)) {
                    case Success(result) => result match {
                      case Right(res)=>complete(res.code,res)
                      case Left(res)=>complete(res.code,res)
                    }
                    case Failure(ex)=>complete(StatusCodes.InternalServerError.intValue,ConversationResponseError(ConversationResponseErrorData(List[String](ex.getMessage)),StatusCodes.InternalServerError.intValue))
                  }
                }
              }
            }
          }
        }
    }
}
