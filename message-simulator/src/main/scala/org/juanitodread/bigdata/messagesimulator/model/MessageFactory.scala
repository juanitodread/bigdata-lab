package org.juanitodread.bigdata.messagesimulator.model

import java.time.LocalDateTime
import java.util.UUID

import scala.util.Random

object MessageFactory {
  private val drivers = Array("Mac", "Linux", "Windows", "iOS", "Android")
  private val types = Array("incoming", "outgoing")
  private val authors = Array("Noemi", "Frida", "Loretito")
  private val contents = Array(
    "This is a sample message",
    "Where is the clouds wear of colors?",
    "Where the horizon announces the calm",
    "Hello",
    "This is a random message",
    "Random access memories",
    "Probably I won't come back never",
    "there's something in your face",
    "I know we have to open eyes",
  )

  def buildRandomMessages(count: Int): List[Message] = {
    Range(0, count).map { _ =>
      Message(
        UUID.randomUUID().toString,
        getDriver(),
        getType(),
        getContent(),
        getAuthor(),
        LocalDateTime.now().toString
      )
    }.toList
  }

  def getDriver(): String = {
    getRandomElement(drivers)
  }

  def getType(): String = {
    getRandomElement(types)
  }

  def getContent(): String = {
    getRandomElement(contents)
  }

  def getAuthor(): String = {
    getRandomElement(authors)
  }

  private def getRandomElement[T](seq: Seq[T]): T = {
    seq(Random.nextInt(seq.size))
  }
}
