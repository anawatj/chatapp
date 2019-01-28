package mappings

import databases.DatabaseComponent
import models.{Contact, ContactItem}
trait ContactTable extends DatabaseComponent {
    import profile.api._

    class ContactTable(tag:Tag) extends Table[Contact](tag,"contacts"){
        def id = column[String]("id",O.PrimaryKey)
        def name = column[String]("name")
        def user_id :Rep[String] = column[String]("user_id")

        def * = (id,name,user_id)<>(Contact.tupled,Contact.unapply)

    }

    val Contacts = TableQuery[ContactTable]

}

trait ContactItemTable extends DatabaseComponent{
    import profile.api._

    class ContactItemTable(tag:Tag) extends Table[ContactItem](tag,"contact_items"){
        def id=column[Int]("id",O.PrimaryKey)
        def contact_id = column[String]("contact_id")
        def user_id = column[String]("user_id")
        def * = (id,contact_id,user_id) <> (ContactItem.tupled,ContactItem.unapply)
    }

    val ContactItems =TableQuery[ContactItemTable]
}
