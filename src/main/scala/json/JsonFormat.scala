package json
import spray.json._
import DefaultJsonProtocol._
object JsonFormat {
  implicit val contactRequestFormat:RootJsonFormat[ContactRequest]=jsonFormat2(ContactRequest)
  implicit val userRequestFormat = jsonFormat4(UserRequest)
  implicit val userResponseDataFormat = jsonFormat5(UserResponseData)
  implicit val userResponseFormat = jsonFormat2(UserResponse)
  implicit val userResponseErrorDataFormat = jsonFormat1(UserResponseErrorData)
  implicit val userResponseErrorFormat = jsonFormat2(UserResponseError)
  implicit val loginRequestFormat = jsonFormat2(LoginRequest)
}
