package ai.wojciechnowak.app

import ai.wojciechnowak.app.functions.{Merging, Reducing, Transforming}
import ai.wojciechnowak.app.model.{IPAddress, IPNumber, Input, Scope}
import org.apache.spark.sql.Dataset
import ai.wojciechnowak.app.Config._

object MainBoot extends App with Transforming with Merging with Reducing {
  import Env.spark.implicits._

  // Data Loading
  val inputDS: Dataset[Input] = InputReader
    .readTable("inputs")
    .as[Input]

  // Preprocessing
  val ipAddressScopeDS: Dataset[Scope[IPAddress]] =
    inputDS.map(inputToIpAddressScope)
      .flatMap(_.toOption)

  val ipNumberScopeDS: Dataset[Scope[IPNumber]] =
    ipAddressScopeDS.map(ipAddressScopeToIpNumberScope)

  val ipNumberSets: Dataset[IPNumber] = ipNumberScopeDS
    .flatMap(ipNumberScopeToIpNumberSet)

  // Reducing & Merging
  val exclusiveNumbers: Dataset[IPNumber] = reduce.apply(ipNumberSets)
  val result: Dataset[Scope[IPNumber]] = merge.apply(exclusiveNumbers)

  // Postprocessing
  val resultAsIPAddress: Dataset[Scope[IPAddress]] = result.map(ipNumberScopeToIpAddressScope)

  // Presenting results
  resultAsIPAddress.show(false)
}
