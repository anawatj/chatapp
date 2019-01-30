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

  implicit val conversationRequestFormat = jsonFormat3(ConversationRequest)

  implicit val conversationResponseDataFormat = jsonFormat3(ConversationResponseData)

  implicit val conversationResponseFormat = jsonFormat2(ConversationResponse)

  implicit val conversationResponseErrorDataFormat = jsonFormat1(ConversationResponseErrorData)
  implicit val conversationResponseErrorFormat = jsonFormat2(ConversationResponseError)

  implicit val messageRequestFormat = jsonFormat4(MessageRequest)

  implicit val messageResponseDataFormat = jsonFormat5(MessageResponseData)
  implicit val messageResponseFormat = jsonFormat2(MessageResponse)

  implicit val messageResponseErrorDataFormat = jsonFormat1(MessageResponseErrorData)
  implicit val messageResponseErrorFormat = jsonFormat2(MessageResponseError)
}
