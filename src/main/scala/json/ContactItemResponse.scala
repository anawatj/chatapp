package json

case class ContactItemResponseData(id:Int,contact_id:String,user_id:String)
case class ContactItemResponse(data:List[ContactItemResponseData],code:Int)
case class ContactItemResponseErrorData(errors:List[String])
case class ContactItemResponseError(error:ContactItemResponseErrorData,code:Int)
