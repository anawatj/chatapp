package databases

import slick.jdbc.H2Profile
trait H2Component extends DatabaseComponent  {

  override val profile = H2Profile

   val db = H2Profile.backend.Database.forConfig("h2mem1")

}
