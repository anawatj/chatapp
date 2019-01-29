package models

trait ConversationType{
  type  Result
  def toRes:Result
}

object ConversationType{

    implicit def convertToConversation(str:String) = new ConversationType {
      override type Result = String
      override def toRes: String = str
    }
}









case class Conversation(id:String,conversation_name:String,conversation_type:ConversationType)

case class ConversationUsers(conversation_id:String,user_id:String)

case class ConversationMessage(conversation_id:String,message_id:String)



