package jdbc

import java.sql.DriverManager

object JdbcTest {
  // See http://www.ne.jp/asahi/hishidama/home/tech/scala/sample/using.html
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
        }
        using(conn.prepareStatement("INSERT INTO t_test VALUES(?,?)")) {
          stmt =>
            val data = Array(
                ("One", 1),("Ichi", 1),("Un", 1),
                ("Two", 2),("Ni", 2), ("Deux", 2),
                ("Three",3), ("San", 3), (null,3)
                )
                
            for (datum <- data) {
              stmt.setString(1, datum._1)
              stmt.setInt(2, datum._2)
              stmt.addBatch();
            }
            stmt.executeBatch();
        }
        
        using(conn.prepareStatement("SELECT * FROM t_test WHERE n=?")) {
          stmt =>
            stmt.setInt(1, 3)
            using(stmt.executeQuery()) {
              rs =>
                // TODO: eliminate loop to look more like functional
                while (rs.next) {
                  val s = rs.getString("s")
                  val result = if (rs.wasNull()) None else Some(s)
                  println(result)
                }
            }

        }
    }
  }
}