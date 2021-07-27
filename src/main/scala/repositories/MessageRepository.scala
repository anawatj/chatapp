package repositories

import databases.MySqlComponent
import mappings.MessageTable
import models.Message

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future


trait MessageRepository extends BaseRepository[Message]{

}
class MessageRepositoryImpl extends MessageRepository with MySqlComponent with MessageTable{
  import profile.api._

  override def add(data: Message): Future[Message] = {
    db.run(Messages+=data) map{
      _ => data
    }
  }

  override def bulkAdd(list: List[Message]): Future[List[Message]] = {
    db.run(Messages++=list) map {
      _ => list
    }
  }



  override def delete(id: String): Future[Unit] = {
    db.run(Messages.filter(_.id===id).delete) map {
      _ =>()
    }
  }

  override def find(id: String): Future[Option[Message]] = {
    db.run(Messages.filter(_.id===id).result.headOption) map {
      message=>message
    }
  }

  override def update(data: Message, id: String): Future[Message] = {
    db.run(Messages.filter(_.id===id).update(data)) map {
      _ => data
    }
  }

}
