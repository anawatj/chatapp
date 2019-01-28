package repositories

import databases.{ MySqlComponent}
import mappings.{UserTable}
import models.{User}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

trait UserRepository extends BaseRepository[User] {

    def findByUserName(username:String):Future[Option[User]]

}

class UserRepositoryImpl extends UserRepository with MySqlComponent with UserTable {
  import profile.api._
  override def add(data: User): Future[User] = {

    db.run(Users+=data) map {
      _ => data
    }

  }

  override def findByUserName(username: String): Future[Option[User]] = {
    db.run(Users.filter(s=>s.username===username).result.headOption) map {
      user=>user
    }
  }

  override def bulkAdd(list: List[User]): Future[List[User]] = {
    db.run(Users++=list) map {
      _ => list
    }
  }

  override def bulkUpdate(list: List[User]): Future[List[User]] = {
    null
  }

  override def delete(id: String): Future[Unit] = {
    null
  }

  override def find(id: String): Future[User] = {
    null
  }

  override def update(data: User, id: String): Future[User] = {
    null
  }

}
