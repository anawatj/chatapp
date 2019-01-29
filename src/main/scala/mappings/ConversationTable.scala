package mappings

import databases.DatabaseComponent
import models.{Conversation, ConversationType}
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

}
