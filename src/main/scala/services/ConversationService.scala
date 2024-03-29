package services

import json._
import models.{Conversation, ConversationType, ConversationUser}
import repositories.{ConversationRepository, ConversationUserRepository,ConversationMessageRepository}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import ConversationType._
import akka.http.scaladsl.model.StatusCodes
import utils.UUIDUtils

class ConversationService(conversationRepository: ConversationRepository, conversationUserRepository: ConversationUserRepository, uuidUtil: UUIDUtils,conversationMessageRepository: ConversationMessageRepository) {


  def createConversation(request: ConversationRequest): Future[Either[ConversationResponseError, ConversationResponse]] = {
    val conversation = Conversation(uuidUtil.generate(), request.conversation_name, request.`type`)
    for {
      conversationDb <- conversationRepository.add(conversation)
      conversationUsers <- Future.successful(request.users.map {
        user => ConversationUser(conversationDb.id, user)
      })
      conversationUsersDb <- conversationUserRepository.bulkAdd(conversationUsers.toList)
    } yield {
      Right(ConversationResponse(ConversationResponseData(conversationDb.id, conversationDb.conversation_name, conversationUsersDb.map(_.user_id)), StatusCodes.Created.intValue))
    }
  }

  def getConversationByUser(user_id: String): Future[Either[ConversationResponseError, ConversationResponseList]] = {
    for {
      conversationUsers <- conversationUserRepository.findByUser(user_id)
      conversations <- conversationRepository.findByIds(conversationUsers.map(_.conversation_id).toList)
      users <- conversationUserRepository.findByConversationIds(conversations.map(_.id).toList)
    } yield {
      val conversationData = conversations.map(conversation=>{
        ConversationResponseData(conversation.id,conversation.conversation_name,users.filter(user=>user.conversation_id==conversation.id).map(_.user_id).toSeq)
      })
      Right(ConversationResponseList(conversationData, StatusCodes.OK.intValue))
    }
  }
  def getConversationDetail(converesation_id: String):Future[Either[ConversationResponseError,ConversationDetailResponse]]={
    for {
      conversationOption <- conversationRepository.find(converesation_id)
      conversationUsers <- conversationUserRepository.findByConversationId(converesation_id)
      conversationMessages <- conversationMessageRepository.findByConversationId(converesation_id)
    } yield  {
      conversationOption match {
         case Some(conversation)=>Right(ConversationDetailResponse(ConversationDetailResponseData(conversation.id,conversation.conversation_name,conversationUsers.map(_.user_id).toSeq,conversationMessages.map(_.message_id).toSeq),StatusCodes.OK.intValue))
         case None => Left(ConversationResponseError(ConversationResponseErrorData(List[String]("Not Found Conversation")),StatusCodes.NotFound.intValue))
       }
    }
  }
}
