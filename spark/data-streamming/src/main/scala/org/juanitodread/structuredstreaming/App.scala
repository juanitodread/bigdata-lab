package org.juanitodread.structuredstreaming

import javax.ws.rs.client.Entity
import org.apache.spark.sql.{ Dataset, Encoders, SparkSession }
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode
import org.juanitodread.structuredstreaming.model.{ KafkaMessage, Log }
import org.juanitodread.structuredstreaming.output.sink.Postgres

object App {
  def main(args: Array[String]): Unit = {
    println("Structured Streaming")

    val spark = SparkSession
      .builder()
      .config("spark.sql.shuffle.partitions", 5)
      .master("local[2]")
      .appName("Structured Streaming")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    val streamInputDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("subscribe", "my-test")
      .load()

    import spark.implicits._
    val kafkaMessageDs: Dataset[KafkaMessage] = streamInputDF
      .selectExpr(
        "cast(key as string) as key",
        "cast(value as string) as json",
        "topic",
        "partition",
        "offset",
        "timestamp",
        "timestampType").as[KafkaMessage]

    val schema = Encoders.product[Log].schema

    val logDS: Dataset[Log] = kafkaMessageDs
      .select(from_json($"json", schema).as("jsonObj"))
      .select(
        "jsonObj.id",
        "jsonObj.device",
        "jsonObj.type",
        "jsonObj.content",
        "jsonObj.created",
        "jsonObj.author").as[Log]

    // Simple aggregation only supports complete output mode
    //    val messagesByAuthorQry = logDS
    //      .map(log => (log.author, log.id))
    //      .groupByKey(pair => pair._1)
    //      .count()
    //      .writeStream
    //      .format("console")
    //      .outputMode(OutputMode.Complete())
    //      .option("truncate", value = false)
    //      .start()
    //    val messagesByAuthorQry = logDS.toDF()
    //      .withWatermark("created", "5 minutes")
    //      .groupBy(window($"created", "5 minutes", "5 minutes"), $"author")
    //      .count()
    //      .writeStream
    //      .format("console")
    //      .outputMode(OutputMode.Update())
    //      .option("truncate", value = false)
    //      .start()
    val messagesByAuthorQry = logDS.toDF()
      .withWatermark("created", "5 minutes")
      .groupBy(window($"created", "5 minutes", "5 minutes"), $"author")
      .count()
      .writeStream
      .foreach(new Postgres("jdbc:postgresql://localhost:5432/data-streaming", "data_streaming", "stream"))
      .outputMode(OutputMode.Update())
      .start()

    messagesByAuthorQry.awaitTermination()
  }
}
