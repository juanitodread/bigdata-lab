package org.juanitodread

import org.apache.spark.{ SparkConf, SparkContext }

object App {
  def main(args: Array[String]): Unit = {
    val file = "test"
    println("Spark App")
    println(s"Analyzing '$file' file")

    val config = new SparkConf()
      .setMaster("local[4]")
      .setAppName("Data Streaming")
    val spark = new SparkContext(config)
    val fileData = spark.textFile(file).cache()
    val words = fileData.flatMap(line => line.split(" "))

    val linesCount = fileData.count()
    val wordsCount = words.count()
    val topWord = words.map(word => (word, 1))
      .reduceByKey(_ + _)
      .sortBy(wordCount => wordCount._2, ascending = false)
      .first()

    println(
      s"""File: "$file"
         |lines: $linesCount
         |words: $wordsCount
         |top word: "${topWord._1}" with ${topWord._2} occurrences
         |""".stripMargin)

    spark.stop()
  }
}
