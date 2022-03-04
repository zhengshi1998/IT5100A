package com.sz.QueryActions

import com.sz.Tables.SlickDB_Book
import com.sz.Tables.SlickDB_Book.Book
import slick.dbio.{DBIO, DBIOAction, NoStream}
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global

object Book_Query {
    val db = Database.forConfig("MySQL_conf")
    val book_table = SlickDB_Book.slick_table

    def execute(sql:  DBIOAction[Any, NoStream, Nothing]): Any = {
        try{
            val res = Await.result(db.run(sql), Duration.Inf)
            println("Action Success!")
            res
        } catch {
            case e: Exception => println("Oooops, something wrong with your program. \n Error info: " + e)
        } finally {
            db.close
        }
    }

    def executeWithFuture(sql: Future[Any]): Any = {
        try{
            val res = Await.result(sql, Duration.Inf)
            println("Action Success!")
            res
        } catch {
            case e: Exception => println("Oooops, something wrong with your program. \n Error info: " + e)
        } finally {
            db.close
        }
    }

    def createTable(): Unit = {
        // create Book table
        execute(DBIO.seq(book_table.schema.create))
    }

    def find_book_by_id(id: Long): Book = {
        var ret: Book = null
        execute(DBIO.seq(book_table.result.map( curList => curList.map(curBook => if (curBook.id.contains(id)) ret = curBook))))
        ret
    }

    def addBook(curBook: Book): Option[Book] = {
        execute(DBIO.seq(book_table += curBook))
        Some(curBook)
    }

    def find_all_books(): List[Book] = {
        var ret: List[Book] = List()
        execute(DBIO.seq(book_table.result.map(curList => curList.map(curBook => ret = ret :+ curBook))))
        ret
    }

    def find_book_by_name(name: String): List[Book] = {
        var ret: List[Book] = List()
        execute(DBIO.seq(book_table.result.map(curList => curList.map(curBook => if(curBook.book_name == name) ret = ret :+ curBook))))
        ret
    }

    def main(args: Array[String]): Unit = {
        delete_book(4)
    }

    def delete_book(id: Long): Unit = {
        val res = executeWithFuture(db.run(book_table.filter(cur => cur.id === id).delete))
    }

    def update_current_owner_id(id: Long, stu_id: Long): Unit = {
        executeWithFuture(db.run(book_table.filter(_.id === id).map(cur => cur.current_owner_id).update(stu_id)))
    }
}
