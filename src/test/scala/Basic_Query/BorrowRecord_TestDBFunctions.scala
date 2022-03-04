package Basic_Query

import com.sz.QueryActions.BorrowRecord_Query
import com.sz.Tables.SlickDB_BorrowRecord
import org.scalatest.flatspec._
import org.scalatest.matchers._

import java.time.LocalDate

class BorrowRecord_TestDBFunctions extends AnyFlatSpec with should.Matchers{

    "createTable() " should "produce a new table in MySQL" in{
        BorrowRecord_Query.createTable()
    }

    "add_record(curRecord: BorrowRecord) " should "insert a new line into MySQL" in {
        val toAdd = SlickDB_BorrowRecord.BorrowRecord(2, LocalDate.now().toString, 1, false)
        BorrowRecord_Query.add_record(toAdd)
    }

    "find_record_by_stu_id(stu_id: Long) " should "return a borrow record by student's id" in {
        println(BorrowRecord_Query.find_record_by_stu_id(1))
    }

    "find_record_by_book_id(stu_id: Long) " should "return a borrow record by book's id" in {
        println(BorrowRecord_Query.find_record_by_book_id(1))
    }

    "find_records_by_stu_id(stu_id: Long) " should "return a borrow record by student's id" in {
        println(BorrowRecord_Query.find_records_by_stu_id(1))
    }

    "find_records_by_book_id(stu_id: Long) " should "return a borrow record by book's id" in {
        println(BorrowRecord_Query.find_records_by_book_id(1))
    }
}
