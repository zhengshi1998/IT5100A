package Library_Query

import com.sz.ExposedDBQueries.Library_Queries.query_unreturned_record
import com.sz.ExposedDBQueries.{Library_CUD, Library_Queries}
import org.scalatest.flatspec._
import org.scalatest.matchers._

class Library_Test extends AnyFlatSpec with should.Matchers{

    "query_all_records_by_stu_code(stu_code: String) " should "return the borrow record based on student code" in{
        println(Library_Queries.query_all_records_by_stu_code("A1111111X"))
    }

    "query_all_books_by_stu_code(stu_code: String) " should "return the borrow record based on student code" in{
        println(Library_Queries.query_all_books_by_stu_code("A1111111X"))
    }

    "add_new_book(name: String)" should "add a new book into library" in {
        Library_CUD.add_new_book("Programming with Node.js")
    }

    "add_new_student(stu_name: String, stu_code: String, gender: String)" should "add a new student into library" in {
        Library_CUD.add_new_student("Jack", "A0123456X", "Male")
    }

    "borrow_book(stu_code: String, book_name: String)" should "create a borrow record and modify book owner info" in {
        Library_CUD.borrow_book("A1222222X", "Programming with C++")
    }

    "return_book(stu_code: String, book_name: String)" should "modify a borrow record and modify book owner info" in {
        Library_CUD.return_book("A1222222X", "Programming with C++")
    }

    "query_stu_with_most_records(num: Int)" should "return a list of students with the highest num borrow records" in {
        println(Library_Queries.query_stu_with_most_records(2))
    }

    "query_unreturned_record() " should "return a list of unreturned records" in{
        println(Library_Queries.query_unreturned_record())
    }

}
