package com.sz.QueryActions

import com.sz.Tables.SlickDB_Student
import com.sz.Tables.SlickDB_Student.Student

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

import scala.Console.println
import scala.concurrent.ExecutionContext.Implicits.global

object Student_Query{
    val db = Database.forConfig("MySQL_conf")
    val stu_table = SlickDB_Student.slick_table

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
        // create Student table
        execute(DBIO.seq(stu_table.schema.create))
    }

    def find_student_by_id(id: Long): Student = {
        var ret: Student = null
        execute(DBIO.seq(stu_table.result.map( curList => curList.map(curStu => if (curStu.id.contains(id)) ret = curStu))))
        ret
    }

    def addStudent(curStu: Student): Option[Student] = {
        execute(DBIO.seq(stu_table += curStu))
        Some(curStu)
    }

    def find_all_students(): List[Student] = {
        var ret: List[Student] = List()
        execute(DBIO.seq(stu_table.result.map(curList => curList.map(curStu => ret = ret :+ curStu))))
        ret
    }

    def find_student_by_name(name: String): List[Student] = {
        var ret: List[Student] = List()
        execute(DBIO.seq(stu_table.result.map(curList => curList.map(curStu => if(curStu.stu_name == name) ret = ret :+ curStu))))
        ret
    }

    def find_student_by_code(stu_code: String): Student = {
        var ret: Student = null
        execute(DBIO.seq(stu_table.result.map(curList => curList.map(curStu => if(curStu.stu_code == stu_code) ret = curStu))))
        ret
    }

    def delete_student(id: Long): Unit = {
        executeWithFuture(db.run(stu_table.filter(cur => cur.id === id).delete))
    }
}