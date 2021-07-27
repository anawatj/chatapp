package models

case class Contact(id:String,name:String,user_id:String)
case class ContactItem(id:Int,contact_id:String,user_id:String)
