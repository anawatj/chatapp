package models

trait ConversationType{

}
object GroupConversationType extends ConversationType
object UserConversationType extends ConversationType

case class Conversation(id:String,conversation_name:String,conversation_type:ConversationType)

case class ConversationUsers(conversation_id:String,user_id:String)

case class ConversationMessage(conversation_id:String,message_id:String)
