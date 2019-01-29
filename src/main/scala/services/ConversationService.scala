package services
import json.{ConversationResponse, ConversationResponseData, ConversationResponseError, UserConversationRequest}
import models.{Conversation, ConversationType, ConversationUser}
import repositories.{ConversationRepository, ConversationUserRepository}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import ConversationType._
import akka.http.scaladsl.model.StatusCodes
import utils.UUIDUtils
class ConversationService(conversationRepository:ConversationRepository,conversationUserRepository: ConversationUserRepository,uuidUtil: UUIDUtils) {


    def createConversation(request:UserConversationRequest):Future[Either[ConversationResponseError,ConversationResponse]]={
        val conversation = Conversation(uuidUtil.generate(),request.conversation_name,request.`type`)
        for{
          conversationDb <- conversationRepository.add(conversation)
          conversationUsers <- Future.successful(request.users.map{
            user=>ConversationUser(conversationDb.id,user)
          })
          conversationUsersDb <- conversationUserRepository.bulkAdd(conversationUsers.toList)
        } yield {
          Right(ConversationResponse(ConversationResponseData(conversationDb.id,conversationDb.conversation_name,conversationUsersDb.map(_.user_id)),StatusCodes.Created.intValue))
        }
    }
}
