package models

trait MessageType{
  def toString:String
}

object MessageType{
  implicit def convertToMessageType(str:String) = new MessageType {
    override def toString: String = str
  }
}
case class Message(id:String,`type`:MessageType,data:String,user_id:String)



