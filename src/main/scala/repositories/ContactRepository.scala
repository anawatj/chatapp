package repositories

import databases.MySqlComponent
import mappings.ContactTable
import models.Contact

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ContactRepository extends BaseRepository[Contact]{

    def findByName(name:String):Future[Option[Contact]]
    def findByUser(user_id:String):Future[Option[Contact]]
}
class ContactRepositoryImpl extends ContactRepository with ContactTable with MySqlComponent{
  import profile.api._
  override def add(data: Contact): Future[Contact] = {
    db.run(Contacts+=data) map {
      _ => data
    }
  }

  override def bulkAdd(list: List[Contact]): Future[List[Contact]] = {
    null
  }

  override def delete(id: String): Future[Unit] =
  {
    null
  }

  override def find(id: String): Future[Option[Contact]] = {
    null
  }

  override def update(data: Contact, id: String): Future[Contact] = {
    null
  }

  override def findByName(name: String): Future[Option[Contact]] = {
    db.run(Contacts.filter(c=>c.name===name).result.headOption) map {
      c=>c
    }
  }

  override def findByUser(user_id: String): Future[Option[Contact]] = {
    db.run(Contacts.filter(c=>c.user_id===user_id).result.headOption) map {
      c=>c
    }
  }
}
