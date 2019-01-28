package repositories

import models.Contact
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ContactRepository extends BaseRepository[Contact]{
}
class ContactRepositoryImpl extends ContactRepository{
  override def add(data: Contact): Future[Contact] = {
    null
  }

  override def bulkAdd(list: List[Contact]): Future[List[Contact]] = {
    null
  }

  override def bulkUpdate(list: List[Contact]): Future[List[Contact]] = {
    null
  }

  override def delete(id: String): Future[Unit] =
  {
    null
  }

  override def find(id: String): Future[Contact] = {
    null
  }

  override def update(data: Contact, id: String): Future[Contact] = {
    null
  }
}
