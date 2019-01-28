package utils

class UUIDUtils {
  def generate(): String  ={
     java.util.UUID.randomUUID().toString
  }
}
