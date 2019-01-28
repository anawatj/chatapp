package models

trait ConversationType{
  override def toString: String

}

object GroupConversationType extends ConversationType {
  override def toString: String = "Group"
}

object UserConversationType extends ConversationType{
  override def toString: String = "User"
}

case class Conversation(id:String,conversation_name:String,conversation_type:ConversationType)

case class ConversationUsers(conversation_id:String,user_id:String)

case class ConversationMessage(conversation_id:String,message_id:String)



