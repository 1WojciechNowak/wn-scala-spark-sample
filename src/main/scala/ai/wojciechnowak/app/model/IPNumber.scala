package ai.wojciechnowak.app.model

case class IPNumber(number: Long)
object IPNumber {

  private val A_MULTIPLIER = 16777216L
  private val B_MULTIPLIER = 65536L
  private val C_MULTIPLIER = 256L

  def apply(ipAddress: IPAddress): IPNumber = IPNumber(
    ipAddress.A * A_MULTIPLIER +
      ipAddress.B * B_MULTIPLIER +
      ipAddress.C * C_MULTIPLIER +
      ipAddress.D)
}