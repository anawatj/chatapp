package models

trait MessageType

object TextMessageType extends MessageType
object ImageMessageType extends MessageType

case class Message(id:String,messageType:MessageType,messageData:Array[Byte],user_id:String)



