package repositories

import databases.MySqlComponent
import mappings.{ConversationMessageTable, ConversationTable, MessageTable}
import models.{ConversationMessage,Message}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ConversationMessageRepository extends BaseRepository[ConversationMessage]{
    def getMessageByConversationId(conversationId:String):Future[List[Message]]
    def findByConversationId(conversationId:String):Future[List[ConversationMessage]]
}

class ConversationMessageRepositoryImpl extends ConversationMessageRepository with MySqlComponent with ConversationMessageTable with MessageTable with ConversationTable {

  import profile.api._
  override def add(data: ConversationMessage): Future[ConversationMessage] = {
      db.run(ConversationMessages+=data) map {
        _ => data
      }
  }

  override def bulkAdd(list: List[ConversationMessage]): Future[List[ConversationMessage]] = {
    db.run(ConversationMessages++=list) map {
      _ => list
    }
  }

  override def delete(id: String): Future[Unit] = {
    null
  }

  override def find(id: String): Future[Option[ConversationMessage]] = {
    null
  }

  override def update(data: ConversationMessage, id: String): Future[ConversationMessage] = {
    null
  }

  override def getMessageByConversationId(conversationId: String): Future[List[Message]] = {
    for {
      conversationMessages <- db.run(ConversationMessages.filter(_.conversation_id===conversationId).result)
      messageIds <- Future(conversationMessages.map(_.message_id))
      messages <- db.run(Messages.filter(_.id.inSet(messageIds)).result)
    } yield {
      messages.toList
    }
  }

  override def findByConversationId(conversationId: String): Future[List[ConversationMessage]] = {
    db.run(ConversationMessages.filter(_.conversation_id===conversationId).result)  map {
      messages=>messages.toList
    }
  }
}
