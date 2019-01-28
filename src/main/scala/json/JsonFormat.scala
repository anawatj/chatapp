package json
import spray.json._
import DefaultJsonProtocol._
object JsonFormat {
  implicit val contactRequestFormat:RootJsonFormat[ContactRequest]=jsonFormat3(ContactRequest)
  implicit val contactResponseDataFormat =jsonFormat4(ContactResponseData)
  implicit val contactResponseFormat = jsonFormat2(ContactResponse)
  implicit val contactResponseErrorDataFormat = jsonFormat1(ContactResponseErrorData)
  implicit val contactResponseErrorFormat = jsonFormat2(ContactResponseError)
  implicit val userRequestFormat = jsonFormat4(UserRequest)
  implicit val userResponseDataFormat = jsonFormat5(UserResponseData)
  implicit val userResponseFormat = jsonFormat2(UserResponse)
  implicit val userResponseErrorDataFormat = jsonFormat1(UserResponseErrorData)
  implicit val userResponseErrorFormat = jsonFormat2(UserResponseError)
  implicit val loginRequestFormat = jsonFormat2(LoginRequest)
}
