package repositories

import databases.MySqlComponent
import mappings.{ContactItemTable, ContactTable}
import models.{ ContactItem}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ContactItemRepository extends BaseRepository[ContactItem]{

    def getByContact(contact_id:String):Future[List[ContactItem]]
}

class ContactItemRepositoryImpl extends ContactItemRepository with MySqlComponent with ContactTable with ContactItemTable{

  import profile.api._
  override def add(data: ContactItem): Future[ContactItem] = {
    null
  }

  override def bulkAdd(list: List[ContactItem]): Future[List[ContactItem]] = {

      db.run(ContactItems++=list).map(
        _=>list
      )

  }

  override def bulkUpdate(list: List[ContactItem]): Future[List[ContactItem]] = {
    null
  }

  override def delete(id: String): Future[Unit] = {
    null
  }

  override def find(id: String): Future[ContactItem] = {
    null
  }

  override def update(data: ContactItem, id: String): Future[ContactItem] = {
    null
  }

  override def getByContact(contact_id: String): Future[List[ContactItem]] = {
    db.run(ContactItems.filter(c=>c.contact_id===contact_id).result).map{
      contactItems=>contactItems.toList
    }
  }




}
