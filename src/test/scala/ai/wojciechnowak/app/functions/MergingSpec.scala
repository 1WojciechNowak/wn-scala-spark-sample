package ai.wojciechnowak.app.functions

import ai.wojciechnowak.app.model.{IPNumber, Scope}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MergingSpec extends AnyWordSpecLike
  with Matchers
  with Merging {

  "Calling toIntervals" should {
    "merge set of sequential numbers to list of intervals" in {
      val ipNumbers = List(
        IPNumber(1),
        IPNumber(2),
        IPNumber(3),
        IPNumber(4),
        IPNumber(7),
        IPNumber(8),
        IPNumber(9))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(Scope(IPNumber(1), IPNumber(4)), Scope(IPNumber(7), IPNumber(9)))
    }

    "merge one element set to List of intervals" in {
      val ipNumbers = List(IPNumber(5))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(Scope(IPNumber(5)))
    }

    "merge with single element at the beginning to List of intervals" in {
      val ipNumbers = List(IPNumber(3),
        IPNumber(8),
        IPNumber(9),
        IPNumber(10))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(Scope(IPNumber(3)), Scope(IPNumber(8),IPNumber(10)))
    }

    "merge with single element in the middle to List of intervals" in {
      val ipNumbers = List(IPNumber(2),
        IPNumber(3),
        IPNumber(7),
        IPNumber(10),
        IPNumber(11),
        IPNumber(12))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(
        Scope(IPNumber(2), IPNumber(3)),
        Scope(IPNumber(7)),
        Scope(IPNumber(10),IPNumber(12))
      )
    }

    "merge with single element at the end to List of intervals" in {
      val ipNumbers = List(IPNumber(5),
        IPNumber(6),
        IPNumber(7),
        IPNumber(8),
        IPNumber(9),
        IPNumber(12))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(Scope(IPNumber(5), IPNumber(9)), Scope(IPNumber(12)))
    }

    "merge two single elements set to List of intervals" in {
      val ipNumbers = List(IPNumber(5), IPNumber(9))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(Scope(IPNumber(5)), Scope(IPNumber(9)))
    }

    "merge with two single elements at the beginning to List of intervals" in {
      val ipNumbers = List(IPNumber(1),
        IPNumber(4),
        IPNumber(8),
        IPNumber(9),
        IPNumber(10))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(
        Scope(IPNumber(1)),
        Scope(IPNumber(4)),
        Scope(IPNumber(8),IPNumber(10)))
    }

    "merge with two single elements in the middle to List of intervals" in {
      val ipNumbers = List(IPNumber(2),
        IPNumber(3),
        IPNumber(7),
        IPNumber(10),
        IPNumber(14),
        IPNumber(15),
        IPNumber(16))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(
        Scope(IPNumber(2), IPNumber(3)),
        Scope(IPNumber(7)),
        Scope(IPNumber(10)),
        Scope(IPNumber(14),IPNumber(16))
      )
    }

    "merge with two single elements at the end to List of intervals" in {
      val ipNumbers = List(IPNumber(5),
        IPNumber(6),
        IPNumber(7),
        IPNumber(8),
        IPNumber(9),
        IPNumber(12),
        IPNumber(17))

      val ipNumberScopes = toScopes(ipNumbers)
      ipNumberScopes shouldBe List(
        Scope(IPNumber(5), IPNumber(9)),
        Scope(IPNumber(12)),
        Scope(IPNumber(17)))
    }

    "merge empty list of numbers" in {
      val ipNumbers = List.empty

      val IPNumberScopes = toScopes(ipNumbers)
      IPNumberScopes shouldBe List.empty
    }
  }
}
