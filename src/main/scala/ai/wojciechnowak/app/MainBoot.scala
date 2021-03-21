package ai.wojciechnowak.app

import ai.wojciechnowak.app.functions.Transforming
import ai.wojciechnowak.app.model.{IPAddress, IPNumber, Input, Scope}
import org.apache.spark.sql.Dataset

import ai.wojciechnowak.app.Config._

object MainBoot extends App with Transforming{

  import Env.spark.implicits._
  val inputDS: Dataset[Input] = InputReader
    .readCsv("simple_input")
    .as[Input]

  val ipAddressTupleDS: Dataset[Scope[IPAddress]] =
    inputDS.map(inputToIpAddressScope)
      .flatMap(_.toOption)

  val ipNumberTupleDS: Dataset[Scope[IPNumber]] =
    ipAddressTupleDS.map(ipAddressScopeToIpNumberScope)
  
  ipNumberTupleDS.show()
}
