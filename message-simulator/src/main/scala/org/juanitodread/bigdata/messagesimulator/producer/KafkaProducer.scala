package org.juanitodread.bigdata.messagesimulator.producer

import java.util.Properties

import io.circe.syntax._
import io.circe.generic.auto._

import org.apache.kafka.clients.producer.{
  ProducerRecord,
  KafkaProducer => KafkaProd
}
import org.juanitodread.bigdata.messagesimulator.model.Message

object KafkaProducer {
  def send(topic: String, message: Message): Unit = {
    val producer = new KafkaProd[String, String](getConfig())
    val record = new ProducerRecord(topic, message.id, message.asJson.noSpaces)
    producer.send(record)
    producer.close()
  }

  def send(topic: String, messages: List[Message]): Unit = {
    val producer = new KafkaProd[String, String](getConfig())

    messages.foreach { message =>
      val record = new ProducerRecord(topic, message.id, message.asJson.noSpaces)
      producer.send(record)
    }
    producer.close()
  }

  def getConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:29092")
    props.put("acks", "all")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }
}
