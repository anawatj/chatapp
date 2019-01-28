package services

import akka.http.scaladsl.model.StatusCodes
import json.{ContactResponse, ContactResponseData, ContactResponseError}

class ContactService {

    def addContact(request:ContactService):Either[ContactResponseError,ContactResponse]={
      Right(ContactResponse(ContactResponseData("1","tong","1"),StatusCodes.OK.intValue))
    }


}
