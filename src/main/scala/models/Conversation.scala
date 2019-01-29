package models

trait ConversationType {
  def toString: String
}

object ConversationType {

  implicit def convertToConversation(str: String) = new ConversationType {
    override def toString: String = str
  }
}

case class Conversation(id: String, conversation_name: String, conversation_type: ConversationType)

case class ConversationUser(conversation_id: String, user_id: String)

case class ConversationMessage(conversation_id: String, message_id: String)



