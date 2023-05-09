package json

case class ConversationResponseData(id:String,conversation_name:String,users:Seq[String])
case class ConversationResponse(data:ConversationResponseData,code:Int)

case class ConversationResponseList(data:List[ConversationResponseData],code:Int)

case class ConversationResponseErrorData(errors:List[String])
case class ConversationResponseError(error:ConversationResponseErrorData,code:Int)

case class ConversationDetailResponseData(id:String,conversation_name:String,users:Seq[String],messages:Seq[String])
case class ConversationDetailResponse(data:ConversationDetailResponseData,code:Int)

