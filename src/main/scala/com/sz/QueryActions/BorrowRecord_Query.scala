package com.sz.QueryActions

import com.sz.Tables.SlickDB_BorrowRecord
import com.sz.Tables.SlickDB_BorrowRecord.BorrowRecord
import slick.dbio.{DBIO, DBIOAction, NoStream}
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global

object BorrowRecord_Query {
    val db = Database.forConfig("MySQL_conf")
    val borrow_table = SlickDB_BorrowRecord.slick_table

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

    def createTable(): Unit = {
        // create Book table
        execute(DBIO.seq(borrow_table.schema.create))
    }

    def find_record_by_stu_id(stu_id: Long): BorrowRecord = {
        var ret: List[BorrowRecord] = List()
        execute(DBIO.seq(borrow_table.result.map( curList => curList.map(curRecord => if (curRecord.stu_id == stu_id) ret = ret :+ curRecord))))
        if(ret.nonEmpty && !ret.head.returned) ret.head else null
    }

    def find_record_by_book_id(book_id: Long): BorrowRecord = {
        var ret: List[BorrowRecord] = List()
        execute(DBIO.seq(borrow_table.result.map( curList => curList.map(curRecord => if (curRecord.book_id == book_id) ret = ret :+ curRecord))))
        if(ret.nonEmpty && !ret.head.returned) ret.head else null
    }

    def find_records_by_stu_id(stu_id: Long): List[BorrowRecord] = {
        var ret: List[BorrowRecord] = List()
        execute(DBIO.seq(borrow_table.result.map( curList => curList.map(curRecord => if (curRecord.stu_id == stu_id) ret = ret :+ curRecord))))
        ret
    }

    def find_records_by_book_id(book_id: Long): List[BorrowRecord] = {
        var ret: List[BorrowRecord] = List()
        execute(DBIO.seq(borrow_table.result.map( curList => curList.map(curRecord => if (curRecord.book_id == book_id) ret = ret :+ curRecord))))
        ret
    }

    def add_record(curRecord: BorrowRecord): Option[BorrowRecord] = {
        execute(DBIO.seq(borrow_table += curRecord))
        Some(curRecord)
    }
}
