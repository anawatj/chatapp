package json

case class ConversationRequest(conversation_name:String,`type`:String,users:Seq[String])
