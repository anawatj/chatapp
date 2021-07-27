package routes
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import json._
import json.JsonFormat._
import services.UserService

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
class UserRoute(userService:UserService)(implicit actorSystem: ActorSystem, actorMaterializer: ActorMaterializer) {

  def route:Route={
    pathPrefix("signup"){
      pathEnd{
        post{
          entity(as[UserRequest]){
            request=>{

              onComplete(userService.signUp(request)){
                case Success(value)=>value match {
                  case Right(res)=>complete(res.code,res)
                  case Left(res)=>complete(res.code,res)
                }
                case Failure(ex)=>complete(StatusCodes.InternalServerError.intValue,UserResponseError(UserResponseErrorData(List[String](ex.getMessage)),StatusCodes.InternalServerError.intValue))
              }
            }

          }

        }
      }
    } ~
    pathPrefix("login"){
      pathEnd{
        post{
          entity(as[LoginRequest]){
            request=>{
              onComplete(userService.logIn(request)){
                case Success(value)=> value match{
                  case Right(res)=>complete(res.code,res)
                  case Left(res)=>complete(res.code,res)
                }
                case Failure(ex)=>complete(StatusCodes.InternalServerError.intValue,UserResponseError(UserResponseErrorData(List[String](ex.getMessage)),StatusCodes.InternalServerError.intValue))
              }
            }
          }
        }
      }
    }
  }
}
