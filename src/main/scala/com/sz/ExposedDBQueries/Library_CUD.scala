package com.sz.ExposedDBQueries

import com.sz.QueryActions.Book_Query
import com.sz.QueryActions.Student_Query
import com.sz.Tables.SlickDB_Book.Book
import com.sz.Tables.SlickDB_BorrowRecord.BorrowRecord
import com.sz.Tables.{SlickDB_Book, SlickDB_BorrowRecord, SlickDB_Student}
import com.sz.Tables.SlickDB_Student.Student
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext.Implicits.global
import java.time.LocalDate
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

object Library_CUD {
    val db = Database.forConfig("MySQL_conf")
    val stu_table = SlickDB_Student.slick_table
    val borrow_table = SlickDB_BorrowRecord.slick_table
    val book_table = SlickDB_Book.slick_table

    def add_new_book(name: String): Unit = {
        Book_Query.addBook(Book(name, LocalDate.now().toString, -1))
    }

    def remove_book(name: String): Unit = {
        Book_Query.find_book_by_name(name).foreach(cur => Book_Query.delete_book(cur.id.get))
    }

    def add_new_student(stu_name: String, stu_code: String, gender: String): Unit = {
        Student_Query.addStudent(Student(stu_name, stu_code, gender))
    }

    def remove_student(stu_code: String): Unit = {
        Student_Query.find_student_by_code(stu_code) match {
            case null =>
            case stu: Student => Student_Query.delete_student(stu.id.get)
        }
    }

    def borrow_book(stu_code: String, book_name: String): Unit = {
        val exec = for {
            id <- stu_table.filter(cur => cur.stu_code === stu_code).map(_.id).result
            book_id <- book_table.filter(curBook => curBook.book_name === book_name).map(_.id).result
            _ <- DBIO.seq(id.map(curStuId => book_table.filter(curBook => curBook.book_name === book_name).map(_.current_owner_id).update(curStuId)): _*)
            _ <- DBIO.seq(id.map(curId => DBIO.seq(book_id.map(curBookId => borrow_table += BorrowRecord(curId, LocalDate.now().toString, curBookId, false)): _*)): _*)
        } yield ()

        try{
            Await.result(db.run(exec.transactionally), Duration.Inf)
            println("Action Success!")
        } catch {
            case e: Exception => println("Oooops, something wrong with your program. \n Error info: " + e)
        } finally {
            db.close
        }
    }

    def return_book(stu_code: String, book_name: String): Unit ={
        val exec = for {
            id <- stu_table.filter(cur => cur.stu_code === stu_code).map(_.id).result
            book_id <- book_table.filter(curBook => curBook.book_name === book_name).map(_.id).result
            _ <- DBIO.seq(id.map(_ => book_table.filter(curBook => curBook.book_name === book_name).map(_.current_owner_id).update(-1)): _*)
            _ <- DBIO.seq(id.map(curId => DBIO.seq(book_id.map(curBookId => borrow_table.filter(curRecord => curRecord.book_id === curBookId && curRecord.stu_id === curId).map(_.returned).update(true)): _*)): _*)
        } yield ()

        try{
            Await.result(db.run(exec.transactionally), Duration.Inf)
            println("Action Success!")
        } catch {
            case e: Exception => println("Oooops, something wrong with your program. \n Error info: " + e)
        } finally {
            db.close
        }
    }
}
