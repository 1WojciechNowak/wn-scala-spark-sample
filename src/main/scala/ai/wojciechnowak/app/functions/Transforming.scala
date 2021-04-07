package ai.wojciechnowak.app.functions

import ai.wojciechnowak.app.model.{IPAddress, IPNumber, Input, Scope}

import scala.util.{Failure, Try}
import scala.util.matching.Regex

trait Transforming {

  // Simplified version of IPV4 regex. It is not enough to validate IPV4 with Regex below!
  private val IPV4_PATTERN: Regex = raw"""([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})""".r
  private val STEP: Long = 1L

  def inputToIpAddressScope(input: Input): Try[Scope[IPAddress]] =
    inputTransformer(input)(toIPAddressTransformer)

  def ipAddressScopeToIpNumberScope(ipAddresses: Scope[IPAddress]): Scope[IPNumber] =
    scopeTransformer(ipAddresses)(ipv4ToNumberTransformer)

  def ipNumberScopeToIpAddressScope(ipNumbers: Scope[IPNumber]): Scope[IPAddress] =
    scopeTransformer(ipNumbers)(ipNumberToIPV4Transformer)

  def ipNumberScopeToIpNumberSet(scope: Scope[IPNumber]): Set[IPNumber] =
    Range.Long.inclusive(scope.start.number, scope.end.number, STEP)
      .map(IPNumber(_))
      .toSet

  private def inputTransformer[T](input: Input)(f: String => Try[T]): Try[Scope[T]] = for {
    left <- f(input.c_left)
    right <- f(input.c_right)
  } yield Scope(left, right)

  private val toIPAddressTransformer: String => Try[IPAddress] = {
    case IPV4_PATTERN(a, b, c, d) => IPAddress(a, b, c, d)
    case _ => Failure(new IllegalArgumentException("Paring error. " +
      "At lease one of arguments do not fit to IPV4 pattern."))
  }

  private def scopeTransformer[A, B](scope: Scope[A])(f: A => B): Scope[B] = scope match {
    case Scope(s, e) => Scope(f(s), f(e))
  }

  private val ipv4ToNumberTransformer: IPAddress => IPNumber = ipv4 => IPNumber(ipv4)
  private val ipNumberToIPV4Transformer: IPNumber => IPAddress = ipNumber => IPAddress(ipNumber)
}

