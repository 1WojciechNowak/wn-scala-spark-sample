package ai.wojciechnowak.app

import ai.wojciechnowak.app.model.IPNumber

object Ordering {
  implicit val ipNumber: Ordering[IPNumber] = new Ordering[IPNumber] {
    override def compare(ip1: IPNumber, ip2: IPNumber): Int =
      ip1.number compare ip2.number
  }
}