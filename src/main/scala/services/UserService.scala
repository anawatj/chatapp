package services

import akka.http.scaladsl.model.StatusCodes
import json._
import models.User
import repositories.UserRepository
import utils.{JwtUtils, PasswordUtils, UUIDUtils}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future


class UserService(userRepository: UserRepository,uuidUtil:UUIDUtils,passwordUtil:PasswordUtils,jwtUtil:JwtUtils) {




    def signUp(request:UserRequest):Future[Either[UserResponseError,UserResponse]]={
      val errors = List[Option[String]](
        request.username.nonEmpty match {
          case true=> None
          case false=>Some("user name is required")
        },
        request.password.nonEmpty match {
          case true=> None
          case false=>Some("password is required")
        },
        request.first_name.nonEmpty match{
          case true=>None
          case false=>Some("first name is required")
        },
        request.last_name.nonEmpty match{
          case true=>None
          case false=>Some("last name is required")
        }
      ).flatten
      errors.nonEmpty match{
        case true=>Future.successful(Left(UserResponseError(UserResponseErrorData(errors),StatusCodes.BadRequest.intValue)))
        case false=>
          userRepository.add(User(uuidUtil.generate().toString,request.username.getOrElse(""),passwordUtil.hash(request.password.getOrElse("")),request.first_name.getOrElse(""),request.last_name.getOrElse(""))) map {
            case user:User => Right(UserResponse(UserResponseData(user.id,user.username,user.password,user.first_name,user.last_name,""),StatusCodes.Created.intValue))
            case _ => Left(UserResponseError(UserResponseErrorData(List[String]("sign in error")),StatusCodes.InternalServerError.intValue))
          }
      }

    }

    def logIn(loginRequest:LoginRequest):Future[Either[UserResponseError,UserResponse]]={
      val errors = List[Option[String]](
          loginRequest.username.nonEmpty match {
            case true=>None
            case false=>Some("username is required")
          },
        loginRequest.password.nonEmpty match{
          case true=>None
          case false=>Some("password is required")
        }
      ).flatten
      errors.nonEmpty match{
        case true=> Future.successful(Left(UserResponseError(UserResponseErrorData(errors),StatusCodes.BadRequest.intValue)))
        case false=>
          userRepository.findByUserName(loginRequest.username.getOrElse("")) map {
            case Some(user)=>
                passwordUtil.isHash(loginRequest.password.getOrElse(""),user.password) match {
                  case true=>Right(UserResponse(UserResponseData(user.id,user.username,user.password,user.first_name,user.last_name,jwtUtil.encode((user.id))),StatusCodes.OK.intValue))
                  case false=>Left(UserResponseError(UserResponseErrorData(List[String]("login failure")),StatusCodes.Unauthorized.intValue))
                }
            case _ => Left(UserResponseError(UserResponseErrorData(List[String]("login failure")),StatusCodes.Unauthorized.intValue))
          }
      }
    }




}
