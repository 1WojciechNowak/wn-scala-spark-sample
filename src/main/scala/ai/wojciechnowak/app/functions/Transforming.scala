package ai.wojciechnowak.app.functions

import ai.wojciechnowak.app.model.{IPAddress, IPNumber, Input, Scope}

import scala.util.{Failure, Try}
import scala.util.matching.Regex

trait Transforming {

  // Simplified version of IPV4 regex. It is not enough to validate IPV4 with Regex below!
  private val IPV4_PATTERN: Regex = raw"""([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})""".r

  def inputToIpAddressScope(input: Input): Try[Scope[IPAddress]] =
    inputTransformer(input)(toIPAddressTransformer)

  def ipAddressScopeToIpNumberScope(ipAddresses: Scope[IPAddress]): Scope[IPNumber] =
    tupleTransformer(ipAddresses)(ipv4ToNumberTransformer)

  private def inputTransformer[T](input: Input)(f: String => Try[T]): Try[Scope[T]] = for {
    left <- f(input.left)
    right <- f(input.right)
  } yield Scope(left, right)

  private val toIPAddressTransformer: String => Try[IPAddress] = {
    case IPV4_PATTERN(a, b, c, d) => IPAddress(a, b, c, d)
    case _ => Failure(new IllegalArgumentException("Paring error. " +
      "At lease one of arguments do not fit to IPV4 pattern."))
  }

  private def tupleTransformer[A, B](scope: Scope[A])(f: A => B): Scope[B] = scope match {
    case Scope(s, e) => Scope(f(s), f(e))
  }

  private val ipv4ToNumberTransformer: IPAddress => IPNumber = ipv4 => IPNumber(ipv4)
}

