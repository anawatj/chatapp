package repositories

import scala.concurrent.Future

trait BaseRepository[T] {

    def add(data:T):Future[T]
    def update(data:T,id:String ):Future[T]
    def delete(id:String):Future[Unit]
    def find(id:String):Future[Option[T]]
    def bulkAdd(list:List[T]):Future[List[T]]
    def bulkUpdate(list:List[T]):Future[List[T]]
}
