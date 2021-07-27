package mappings

import databases.DatabaseComponent
import models.{ConversationType, Message, MessageType}

trait MessageTable extends DatabaseComponent{
  import profile.api._

  implicit val messageTypeMapper = MappedColumnType.base[MessageType, String](
    e => e.toString,
    s => s
  )


  class MessageTable(tag:Tag) extends Table[Message](tag,"messages"){
    def id = column[String]("id",O.PrimaryKey)
    def `type` = column[MessageType]("type")
    def  data = column[String]("data")
    def user_id = column[String]("user_id")

    def * =(id,`type`,data,user_id)<>(Message.tupled,Message.unapply)
  }

  val Messages = TableQuery[MessageTable]
}
