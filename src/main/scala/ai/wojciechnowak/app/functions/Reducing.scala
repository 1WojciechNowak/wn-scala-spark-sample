package ai.wojciechnowak.app.functions

import ai.wojciechnowak.app.Columns.COUNT
import ai.wojciechnowak.app.Env
import ai.wojciechnowak.app.model.IPNumber
import ai.wojciechnowak.app.model.IPNumber.NUMBER
import org.apache.spark.sql.Dataset

trait Reducing {
  import Env.spark.implicits._
  val reduce: Dataset[IPNumber] => Dataset[IPNumber] = { ipNumbers =>
    ipNumbers
      .groupBy(NUMBER)
      .count()
      .filter(COUNT === 1)
      .drop(COUNT)
      .sort(NUMBER)
      .as[IPNumber]
  }
}