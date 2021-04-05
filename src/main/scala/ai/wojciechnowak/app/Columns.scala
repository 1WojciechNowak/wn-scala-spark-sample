package ai.wojciechnowak.app

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.col

object Columns {
  val COUNT: Column = col("COUNT")
}
