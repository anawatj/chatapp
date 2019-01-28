import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import routes.{ContactRoute, UserRoute}
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import repositories.{ContactItemRepositoryImpl, ContactRepositoryImpl, UserRepositoryImpl}
import services.{ContactService, UserService}
import utils.{PasswordUtils, UUIDUtils}

import scala.concurrent.Future
import scala.io.StdIn

object MainApp  {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  def main(args:Array[String])={
    val userRepository = new UserRepositoryImpl
    val contactRepository = new ContactRepositoryImpl
    val contactItemRepository = new ContactItemRepositoryImpl
    val uuidUtil = new UUIDUtils
    val passwordUtil =new PasswordUtils
    val userService = new UserService(userRepository,uuidUtil,passwordUtil)
    val contactService = new ContactService(contactRepository,contactItemRepository,uuidUtil)
    val userRoute = new UserRoute(userService)
    val contactRoute = new ContactRoute(contactService)
    val route = pathPrefix("api"/"v1"){
      contactRoute.route ~
      userRoute.route
    }

    val (host, port) = ("0.0.0.0", 9000)
    val bindingFuture: Future[ServerBinding] =
      Http().bindAndHandle(route, host, port)
  }



}