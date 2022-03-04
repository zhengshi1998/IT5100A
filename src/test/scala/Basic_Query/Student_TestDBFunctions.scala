package Basic_Query

import com.sz.QueryActions.Student_Query
import com.sz.Tables.SlickDB_Student
import org.scalatest.flatspec._
import org.scalatest.matchers._

class Student_TestDBFunctions extends AnyFlatSpec with should.Matchers{

    "createTable() " should "produce a new table in MySQL" in{
        Student_Query.createTable()
    }

    "addStudent(curStu: Student) " should "insert a new line into MySQL" in {
        val toAdd = SlickDB_Student.Student("Tom", "A1222222X", "Male")
        Student_Query.addStudent(toAdd)
    }

    "delete_student(id: Long)" should "delete a line from MySQL" in {
        Student_Query.delete_student(6)
    }

    "find_student_by_id(id: Long) " should "return a student by his/her id" in {
        println(Student_Query.find_student_by_id(4))
    }

    "find_all_students() " should "return a list of all students" in {
        println(Student_Query.find_all_students())
    }

    "find_student_by_name(name: String) " should "return a student by his/her name" in {
        println(Student_Query.find_student_by_name("Jane"))
    }

    "find_student_by_code(stu_code: String) " should "return a student by his/her stu_code" in {
        println(Student_Query.find_student_by_code("A1222222X"))
    }
}
