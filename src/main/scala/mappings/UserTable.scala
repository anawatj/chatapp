package mappings

import databases.DatabaseComponent
import models.{User}


trait UserTable extends DatabaseComponent {

  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "users") {


    def id = column[String]("user_id", O.PrimaryKey)

    def username = column[String]("username")

    def password = column[String]("password")

    def first_name = column[String]("first_name")

    def last_name = column[String]("last_name")

    def * = (id, username, password,first_name,last_name)<>(User.tupled, User.unapply)
  }
  val Users = TableQuery[UserTable]


}

