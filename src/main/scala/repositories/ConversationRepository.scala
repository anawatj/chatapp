package repositories

import databases.MySqlComponent
import mappings.ConversationTable
import models.Conversation

import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
trait ConversationRepository extends BaseRepository[Conversation]{


}

class ConversationRepositoryImpl extends ConversationRepository with MySqlComponent with ConversationTable{
  import profile.api._
  override def add(data: Conversation): Future[Conversation] = {
    db.run(Conversations+=data) map {
      _ => data
    }
  }

  override def bulkAdd(list: List[Conversation]): Future[List[Conversation]] = {
    db.run(Conversations++=list) map {
      _ => list
    }
  }


  override def delete(id: String): Future[Unit] = {
    db.run(Conversations.filter(_.id===id).delete) map {
      _ => ()
    }
  }

  override def find(id: String): Future[Option[Conversation]] = {
    db.run(Conversations.filter(_.id===id).result.headOption) map {
      conversation => conversation
    }
  }

  override def update(data: Conversation, id: String): Future[Conversation] = {
    null
  }

}
