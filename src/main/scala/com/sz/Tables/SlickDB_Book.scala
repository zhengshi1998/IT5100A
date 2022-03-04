package com.sz.Tables

import slick.jdbc.MySQLProfile.api._

object SlickDB_Book{
    case class Book(book_name: String, create_date: String, current_owner_id: Long, id: Option[Long] = None)

    class Book__SlickDB_TableModel(tag: Tag) extends Table[Book](tag, "Books"){
        def id: Rep[Long] = column[Long] ("id", O.PrimaryKey, O.AutoInc)
        def book_name: Rep[String] = column[String]("book_name")
        def create_date: Rep[String] = column[String]("create_date")
        def current_owner_id: Rep[Long] = column[Long]("current_owner_id")

        def * = (book_name, create_date, current_owner_id, id.?) <> (Book.tupled, Book.unapply)
    }

    def slick_table = TableQuery[Book__SlickDB_TableModel]
}