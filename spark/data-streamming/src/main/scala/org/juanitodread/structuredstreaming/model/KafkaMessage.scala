package org.juanitodread.structuredstreaming.model

import java.sql.Timestamp

case class KafkaMessage(
  key: String,
  topic: String,
  partition: Int,
  offset: Long,
  timestamp: Timestamp,
  timestampType: Int)

case class Log(
  id: String,
  device: String,
  `type`: String,
  content: String,
  created: Timestamp,
  author: String)