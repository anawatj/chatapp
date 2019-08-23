import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import routes._
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import repositories._
import services.{ContactService, ConversationService, MessageService, UserService}
import utils.{JwtUtils, PasswordUtils, UUIDUtils}

import scala.concurrent.Future
import scala.io.StdIn

object MainApp  {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  def main(args:Array[String])={
    val userRepository = new UserRepositoryImpl
    val contactRepository = new ContactRepositoryImpl
    val contactItemRepository = new ContactItemRepositoryImpl
    val conversationRepository = new ConversationRepositoryImpl
    val conversationUserRepository = new ConversationUserRepositoryImpl
    val messageRepository = new MessageRepositoryImpl
    val conversationMessageRepository = new ConversationMessageRepositoryImpl
    val uuidUtil = new UUIDUtils
    val passwordUtil =new PasswordUtils
    val jwtUtil = new JwtUtils
    val userService = new UserService(userRepository,uuidUtil,passwordUtil,jwtUtil)
    val contactService = new ContactService(contactRepository,contactItemRepository,uuidUtil)
    val conversationService = new ConversationService(conversationRepository,conversationUserRepository,uuidUtil)
    val messageService = new MessageService(messageRepository,conversationMessageRepository,uuidUtil)
    val userRoute = new UserRoute(userService)
    val contactRoute = new ContactRoute(contactService,jwtUtil)
    val conversationRoute = new ConversationRoute(conversationService)
    val messageRoute = new MessageRoute(messageService)
    val websocketRoute = new WebSocketRoute
    val route = pathPrefix("api"/"v1"){
      contactRoute.route ~
      userRoute.route ~
      conversationRoute.route ~
      messageRoute.route

    }

    val socketRoute = pathPrefix("api"/"v1"){
      websocketRoute.route
    }



    val (host, port) = ("0.0.0.0", 9000)
    val bindingFuture: Future[ServerBinding] =
      Http().bindAndHandle(route, host, port)

    val socketBinding: Future[ServerBinding] =
      Http().bindAndHandle(socketRoute, host, 9091)
  }



}