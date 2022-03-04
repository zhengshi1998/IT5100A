package Basic_Query

import com.sz.QueryActions.Book_Query
import com.sz.Tables.SlickDB_Book
import org.scalatest.flatspec._
import org.scalatest.matchers._

import java.time.LocalDate

class Book_TestDBFunctions extends AnyFlatSpec with should.Matchers{

    "createTable() " should "produce a new table in MySQL" in{
        Book_Query.createTable()
    }

    "addBook(curStu: Student) " should "insert a new line into MySQL" in {
        val toAdd = SlickDB_Book.Book("Programming with C++", LocalDate.now().toString, -1)
        Book_Query.addBook(toAdd)
    }

    "find_book_by_id(id: Long) " should "return a book by its id" in {
        println(Book_Query.find_book_by_id(2))
    }

    "find_all_books() " should "return a list of all books" in {
        println(Book_Query.find_all_books())
    }

    "find_book_by_name(name: String) " should "return a book by its name" in {
        println(Book_Query.find_book_by_name("Programming with C++"))
    }

    "delete_book(id: Long" should "delete a book with given id" in {
        println(Book_Query.delete_book(1))
    }

    "update_current_owner_id(id: Long, stu_id: Long)" should "modify the owner id of a certain book" in {
        Book_Query.update_current_owner_id(2, 5)
    }
}
