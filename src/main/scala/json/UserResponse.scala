package json



case class UserResponseData(id:String,username:String,password:String,first_name:String,last_name:String)
case class UserResponse(data:UserResponseData,code:Int)
case class UserResponseErrorData(errors:List[String])
case class UserResponseError(error:UserResponseErrorData,code:Int)
