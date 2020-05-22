package org.juanitodread.bigdata.messagesimulator

import org.juanitodread.bigdata.messagesimulator.model.MessageFactory
import org.juanitodread.bigdata.messagesimulator.producer.KafkaProducer

object App {
  def main(args: Array[String]): Unit = {
    val msgsCount = if (args.length > 0) args(0).toInt else 10
    val topic = if (args.length > 1) args(1) else "my-topic"
    println(s"Message Simulator: topic=$topic,messages=$msgsCount")

    val msgs = MessageFactory.buildRandomMessages(msgsCount)
    KafkaProducer.send(topic, msgs)
    println("Message Simulator: Messages sent")
  }
}
