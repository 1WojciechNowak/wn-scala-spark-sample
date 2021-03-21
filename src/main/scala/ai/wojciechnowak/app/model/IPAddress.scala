package ai.wojciechnowak.app.model

import scala.util.Try

case class IPAddress(A: Int, B: Int, C: Int, D: Int)
object IPAddress {
  def apply(A: String,
            B: String,
            C: String,
            D: String): Try[IPAddress] =
    Try { IPAddress(A.toInt, B.toInt, C.toInt, D.toInt) }
}

