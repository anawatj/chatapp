package json

case class MessageRequest(conversation_id:Option[String],data:Option[String],`type`:String,user_id:Option[String])
