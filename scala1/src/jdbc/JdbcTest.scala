package jdbc

import java.sql.{ Array => _, _ }
object JdbcTest {
  def using[T <% { def close(): Unit }](r: T)(f: T => Any) {
    try f(r) finally r.close
  }

  def main(args: Array[String]) {
    val url = "jdbc:derby:memory:test;create=true"

    using(DriverManager.getConnection(url)) { conn =>
      using(conn.createStatement()) { stmt =>
        stmt.execute("CREATE TABLE t_test (s VARCHAR(8),n INTEGER)")
        stmt.executeUpdate("INSERT INTO t_test VALUES('John',1)")
        stmt.executeUpdate("INSERT INTO t_test VALUES('Allen',2)")
        stmt.executeUpdate("INSERT INTO t_test VALUES('Marcel',3)")
      }
    }
  }
}