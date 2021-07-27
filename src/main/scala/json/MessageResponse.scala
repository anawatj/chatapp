package json

case class MessageResponseData(id:String,`type`:String,data:String,conversation_id:String,user_id:String)
case class MessageResponse(data:MessageResponseData,code:Int)

case class MessageResponseErrorData(errors:List[String])
case class MessageResponseError(error:MessageResponseErrorData,code:Int)