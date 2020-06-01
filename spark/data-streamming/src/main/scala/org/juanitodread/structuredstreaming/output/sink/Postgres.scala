package org.juanitodread.structuredstreaming.output.sink

import java.sql.{ Connection, DriverManager, PreparedStatement }

import org.apache.spark.sql.{ ForeachWriter, Row }

class Postgres(url: String, user: String, pass: String) extends ForeachWriter[Row] {
  private var connection: Connection = _
  private var preparedStatement: PreparedStatement = _

  override def open(partitionId: Long, epochId: Long): Boolean = {
    Class.forName("org.postgresql.Driver")
    connection = DriverManager.getConnection(url, user, pass)
    preparedStatement = connection.prepareStatement("INSERT INTO author(author, messages) values (?, ?)")
    true
  }

  override def process(value: Row): Unit = {
    preparedStatement.setString(1, value.getAs[String]("author"))
    preparedStatement.setLong(2, value.getAs[Long]("count"))
    preparedStatement.executeUpdate()
  }

  override def close(errorOrNull: Throwable): Unit = {
    connection.close()
  }
}
