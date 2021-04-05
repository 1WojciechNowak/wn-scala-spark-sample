package ai.wojciechnowak.app.model

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.col

case class IPNumber(number: Long) {
  def getNext: IPNumber = new IPNumber(this.number + 1)
}

object IPNumber {
  val NUMBER: Column = col("number")

  private val A_MULTIPLIER = 16777216L
  private val B_MULTIPLIER = 65536L
  private val C_MULTIPLIER = 256L

  def apply(ipAddress: IPAddress): IPNumber = IPNumber(
    ipAddress.A * A_MULTIPLIER +
      ipAddress.B * B_MULTIPLIER +
      ipAddress.C * C_MULTIPLIER +
      ipAddress.D)
}