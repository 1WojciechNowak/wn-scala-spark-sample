package ai.wojciechnowak.app.model

import scala.util.Try

case class IPAddress(A: Int, B: Int, C: Int, D: Int)
object IPAddress {
  def apply(A: String,
            B: String,
            C: String,
            D: String): Try[IPAddress] =
    Try { IPAddress(A.toInt, B.toInt, C.toInt, D.toInt) }

  def apply(ipNumber: IPNumber): IPAddress = {
    val d = ipNumber.number & 0xff
    val c = ipNumber.number >> 8 & 0xff
    val b = ipNumber.number >> 8 >> 8 & 0xff
    val a = ipNumber.number >> 8 >> 8 >> 8 & 0xff
    IPAddress(a.toInt, b.toInt, c.toInt, d.toInt)
  }
}