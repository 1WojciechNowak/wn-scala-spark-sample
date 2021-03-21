package ai.wojciechnowak.app.functions

import ai.wojciechnowak.app.model.{IPAddress, Input, Scope}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class TransformingSpec extends AnyWordSpecLike
  with Matchers
  with Transforming {

  "Calling inputToIPAddress" should {
    "succeed with transform input to Scope of IPAddress" in {
      val testInput = Input("201.233.7.160", "201.233.7.168")

      val ipAddressTuple = inputToIpAddressScope(testInput)
      assert(ipAddressTuple.isSuccess)
      ipAddressTuple.get.start shouldBe IPAddress(201,233,7,160)
      ipAddressTuple.get.end shouldBe IPAddress(201,233,7,168)
    }

    "fail with transform input to Scope of IPAddress on wrong both 'input.left' and 'input.right'" in {
      val testInput = Input("19X.233.OP.1D8", "blah")

      val ipAddressTuple = inputToIpAddressScope(testInput)
      assert(ipAddressTuple.isFailure)
    }

    "fail with transform input to Scope of IPAddress on wrong 'input.left'" in {
      val testInput = Input("blah", "201.233.7.168")

      val ipAddressTuple = inputToIpAddressScope(testInput)
      assert(ipAddressTuple.isFailure)
    }

    "fail with transform input to Scope of IPAddress on wrong 'input.right'" in {
      val testInput = Input("197.233.7.168", "blah")

      val ipAddressTuple = inputToIpAddressScope(testInput)
      assert(ipAddressTuple.isFailure)
    }
  }

  "Calling ipAddressTupleToIpNumberTuple" should {
    "transform ipAddress to ipNumber" in {
      val testIPAddressTuple = Scope(IPAddress(197,233,7,168), IPAddress(201,233,7,160))

      val ipNumberTuple = ipAddressScopeToIpNumberScope(testIPAddressTuple)
      ipNumberTuple.start.number shouldBe 3320383400L
      ipNumberTuple.end.number shouldBe 3387492256L
    }
  }
}
