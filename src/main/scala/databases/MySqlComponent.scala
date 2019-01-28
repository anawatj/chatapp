package databases

import slick.jdbc.MySQLProfile
trait MySqlComponent extends DatabaseComponent  {

  override val profile =MySQLProfile

  val db = profile.backend.Database.forConfig("mysql")

}
