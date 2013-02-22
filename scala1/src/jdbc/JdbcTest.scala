package jdbc

import java.sql.{ Array => _, _ }
import scala.collection.immutable.Nil
object JdbcTest {
  def using[T <% { def close(): Unit }](r: T)(f: T => Any) {
    try f(r) finally r.close
  }

  def main(args: Array[String]) {
    val url = "jdbc:derby:memory:test;create=true"

    using(DriverManager.getConnection(url)) {
      conn =>
        using(conn.createStatement()) {
          stmt =>
            stmt.execute("CREATE TABLE t_test (s VARCHAR(8),n INTEGER)")
            stmt.executeUpdate("INSERT INTO t_test VALUES('John',1)")
            stmt.executeUpdate("INSERT INTO t_test VALUES('Juan',1)")
            stmt.executeUpdate("INSERT INTO t_test VALUES('Paul',2)")
            stmt.executeUpdate("INSERT INTO t_test VALUES('Allen',2)")
            stmt.executeUpdate("INSERT INTO t_test VALUES('Marcel',3)")
            stmt.executeUpdate("INSERT INTO t_test VALUES(NULL,3)")
            stmt.executeUpdate("INSERT INTO t_test VALUES('Proust',3)")
        }
        using(conn.prepareStatement("SELECT * FROM t_test WHERE n=?")) {
          stmt => 
            stmt.setInt(1, 3)
            using(stmt.executeQuery()) {
              rs => 
                // TODO: eliminate loop to look more like functional
                while (rs.next) {
                  val s = rs.getString("s")
                  val result = if(rs.wasNull()) None else Some(s)
                  println(result)
                }
            }

        }
    }
  }
}