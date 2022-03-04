package com.sz.Tables

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

object SlickDB_BorrowRecord{
    case class BorrowRecord(stu_id: Long, borrow_date: String, book_id: Long, returned: Boolean, id: Option[Long] = None)

    class BorrowRecord__SlickDB_TableModel(tag: Tag) extends Table[BorrowRecord](tag, "Borrow_Records"){
        def id: Rep[Long] = column[Long] ("id", O.PrimaryKey, O.AutoInc)
        def stu_id: Rep[Long] = column[Long]("stu_code")
        def borrow_date: Rep[String] = column[String]("borrow_date")
        def book_id: Rep[Long] = column[Long]("book_id")
        def returned: Rep[Boolean] = column[Boolean]("returned")

        def * : ProvenShape[BorrowRecord] = (stu_id, borrow_date, book_id, returned, id.?) <> (BorrowRecord.tupled, BorrowRecord.unapply)
    }

    def slick_table = TableQuery[BorrowRecord__SlickDB_TableModel]
}
