package mappings

import databases.DatabaseComponent
import models.{Conversation, ConversationType, ConversationUser}
import models.ConversationType._
trait ConversationTable extends DatabaseComponent{

  import profile.api._
  implicit val conversationTypeMapper = MappedColumnType.base[ConversationType, String](
    e => e.toString,
    s => s
  )

  class ConversationTable(tag:Tag) extends Table[Conversation](tag,"conversations"){
    def id=column[String]("id",O.PrimaryKey)
    def conversation_name = column[String]("conversation_name")
    def conversation_type = column[ConversationType]("conversation_type")

    def * = (id,conversation_name,conversation_type)<>(Conversation.tupled,Conversation.unapply)

  }

  val Conversations = TableQuery[ConversationTable]

}

trait ConversationUserTable extends DatabaseComponent{
   import profile.api._

    class ConversationUserTable(tag:Tag) extends Table[ConversationUser](tag,"conversation_users"){
        def conversation_id = column[String]("conversation_id",O.PrimaryKey)
        def user_id = column[String]("user_id",O.PrimaryKey)
        def * = (conversation_id,user_id)<>(ConversationUser.tupled,ConversationUser.unapply)
    }

  val ConversationUsers = TableQuery[ConversationUserTable]
}
