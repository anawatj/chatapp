package services

import json.{ContactItemResponse, ContactItemResponseData, ContactItemResponseError, ContactRequest, ContactResponse, ContactResponseData, ContactResponseError}
import models.{Contact, ContactItem}
import repositories.{ContactItemRepository, ContactRepository}
import utils.UUIDUtils

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future


class ContactService(contactRepository: ContactRepository,contactItemRepository:ContactItemRepository,uuidUtil:UUIDUtils) {

   def uploadContact(contactRequest:ContactRequest):Future[Either[ContactResponseError,ContactResponse]]={
        contactRepository.findByName(contactRequest.name.getOrElse("")) flatMap  {
          case Some(contact)=>updateContact(contactRequest,contact.id)
          case None=>addContact(contactRequest)
        }

   }

    def updateContact(contactRequest:ContactRequest,id:String):Future[Either[ContactResponseError,ContactResponse]]={
      val contact=Contact(id,contactRequest.name.getOrElse(""),contactRequest.user_id.getOrElse(""))
      for{
        contactItemsDb<-contactItemRepository.getByContact(id)

        contactItems<- Future.successful(contactRequest.users.diff(contactItemsDb.map(c=>c.user_id)).map(user_id=>ContactItem(0,contact.id,user_id)))

        contactItemsRet<- contactItemRepository.bulkAdd(contactItems.toList)
      } yield {
        Right(ContactResponse(ContactResponseData(contact.id,contact.name,contact.user_id,contactItems.map(ci=>ci.user_id)),200))
      }
    }

    def addContact(contactRequest: ContactRequest):Future[Either[ContactResponseError,ContactResponse]]={
      val contact = Contact(uuidUtil.generate(),contactRequest.name.getOrElse(""),contactRequest.user_id.getOrElse(""))

      for{
        contactDb <- contactRepository.add(contact)
        contactItems <- Future.successful(contactRequest.users.map{
          user=>ContactItem(0,contactDb.id,user)
        })
        contactItemsDb <- contactItemRepository.bulkAdd(contactItems.toList)
      } yield {
        Right(ContactResponse(ContactResponseData(contactDb.id, contactDb.name, contactDb.user_id, contactItemsDb.map(ci => ci.user_id).toSeq), 201))

      }

    }

    def findByUser(user_id:String):Future[Either[ContactItemResponseError,ContactItemResponse]]={
      for {
        contactOption <- contactRepository.findByUser(user_id)
        contactItems <- contactOption match {
          case  Some(contact)=>contactItemRepository.getByContact(contact.id)
          case  _ =>Future.successful(List[ContactItem]())
        }
      } yield  {
        Right(ContactItemResponse(contactItems.map(contactItem=>ContactItemResponseData(contactItem.id,contactItem.contact_id,contactItem.user_id)),200))
      }
    }


}
