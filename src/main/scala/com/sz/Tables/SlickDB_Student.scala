package com.sz.Tables

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape


object SlickDB_Student{
    case class Student(stu_name: String, stu_code: String, gender: String, id: Option[Long] = None)

    class Student__SlickDB_TableModel(tag: Tag) extends Table[Student](tag, "Students"){
        def id: Rep[Long] = column[Long] ("id", O.PrimaryKey, O.AutoInc)
        def stu_name: Rep[String] = column[String]("stu_name")
        def stu_code: Rep[String] = column[String]("stu_code", O.Unique)
        def gender: Rep[String] = column[String]("gender")

        def * : ProvenShape[Student] = (stu_name, stu_code, gender, id.?) <> (Student.tupled, Student.unapply)
    }

    def slick_table = TableQuery[Student__SlickDB_TableModel]
}