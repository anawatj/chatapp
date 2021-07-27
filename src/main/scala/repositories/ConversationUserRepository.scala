package repositories

import databases.MySqlComponent
import mappings.{ConversationTable, ConversationUserTable}
import models.{ConversationType, ConversationUser}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ConversationUserRepository extends BaseRepository [ConversationUser]{

}
class ConversationUserRepositoryImpl extends ConversationUserRepository with MySqlComponent with ConversationTable with ConversationUserTable{
  import profile.api._

  override def add(data: ConversationUser): Future[ConversationUser] = {
      db.run(ConversationUsers+=data) map {
        _ => data
      }
  }

  override def bulkAdd(list: List[ConversationUser]): Future[List[ConversationUser]] = {
    db.run(ConversationUsers++=list) map{
      _ => list
    }
  }


  override def delete(id: String): Future[Unit] = {
    null
  }

  override def find(id: String): Future[Option[ConversationUser]] = {
    null
  }

  override def update(data: ConversationUser, id: String): Future[ConversationUser] = {
    null
  }
}
