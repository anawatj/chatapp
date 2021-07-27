package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes

import json._
import json.JsonFormat._

import scala.util.{Failure, Success}
import services.MessageService
import utils.JwtUtils

class MessageRoute(messageService: MessageService,jwtUtils:JwtUtils)(implicit actorSystem: ActorSystem, actorMaterializer: ActorMaterializer)  {


  def route: Route  = {
    pathPrefix("messages"){
      pathEnd{
        post{
          (headerValueByName("Authorization")) {
            header => {
              jwtUtils.decode(header) match {
                case Success(auth) => {
                  entity(as[MessageRequest]){
                    request=>{
                      onComplete(messageService.sendMessage(request)) {
                        case Success(result) => result match {
                          case Right(res)=>complete(res.code,res)
                          case Left(res)=>complete(res.code,res)
                        }
                        case Failure(ex)=>complete(StatusCodes.InternalServerError.intValue,MessageResponseError(MessageResponseErrorData(List[String](ex.getMessage)),StatusCodes.InternalServerError.intValue))
                      }
                    }
                  }
                }
                case Failure(ex)=>complete(StatusCodes.Unauthorized.intValue,MessageResponseError(MessageResponseErrorData(List[String](ex.getMessage)),StatusCodes.Unauthorized.intValue))
              }
            }
          }

        }
      }
    }
  }
}
