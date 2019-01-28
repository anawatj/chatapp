package json

case class ContactResponseData(id:String,name:String,user_id:String)
case class ContactResponse(data:ContactResponseData,code:Int)

case class ContactResponseErrorData(errors:List[String])
case class ContactResponseError(error:ContactResponseErrorData,code:Int)


