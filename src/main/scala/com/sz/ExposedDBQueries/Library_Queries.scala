package com.sz.ExposedDBQueries

import com.sz.Tables.SlickDB_BorrowRecord.BorrowRecord
import com.sz.QueryActions.Book_Query
import com.sz.QueryActions.Student_Query
import com.sz.QueryActions.BorrowRecord_Query
import com.sz.Tables.SlickDB_Book.Book
import com.sz.Tables.{SlickDB_Book, SlickDB_BorrowRecord, SlickDB_Student}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Library_Queries {
    val db = Database.forConfig("MySQL_conf")
    val stu_table = SlickDB_Student.slick_table
    val borrow_table = SlickDB_BorrowRecord.slick_table
    val book_table = SlickDB_Book.slick_table

    def query_unreturned_record(): List[BorrowRecord] = {
        val action = borrow_table.filter(!_.returned).result
        try{
            val ret = Await.result(db.run(action), Duration.Inf)
            println("Action Success!")
            ret.toList
        } catch {
            case e: Exception =>
                println("Oooops, something wrong with your program. \n Error info: " + e)
                null

        } finally {
            db.close
        }
    }

    //give a student code, this function returns all the borrow records of a specific student
    def query_all_records_by_stu_code(stu_code: String): List[BorrowRecord] = {
        Student_Query.find_student_by_code(stu_code) match {
            case null  => null
            case c => BorrowRecord_Query.find_records_by_stu_id(c.id.get)
        }
    }

    //give a student code, this function returns the unreturned books borrowed by a specific student
    def query_all_books_by_stu_code(stu_code: String): List[Book] = {
        Student_Query.find_student_by_code(stu_code) match {
            case null  => null
            case c => BorrowRecord_Query.find_records_by_stu_id(c.id.get).filter(br => !br.returned).map(br_nonReturned => Book_Query.find_book_by_id(br_nonReturned.book_id))
        }
    }

    def query_stu_with_most_records(num: Int): List[(String, String, String, Option[Long], Int)] = {
        val action = sql"SELECT tmp.* FROM (SELECT stu.*, COUNT(*) AS num FROM students stu INNER JOIN borrow_records br ON br.stu_code = stu.id GROUP BY br.stu_code ORDER BY num DESC LIMIT 3) AS tmp".as[(String, String, String, Option[Long], Int)]

        try{
            val ret = Await.result(db.run(action), Duration.Inf)
            println("Action Success!")
            ret.toList
        } catch {
            case e: Exception =>
                println("Oooops, something wrong with your program. \n Error info: " + e)
                null

        } finally {
            db.close
        }
    }
}
