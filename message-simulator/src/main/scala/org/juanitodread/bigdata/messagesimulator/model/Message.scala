package org.juanitodread.bigdata.messagesimulator.model

case class Message(
  id: String,
  device: String,
  `type`: String,
  content: String,
  author: String,
  created: String)
