package ai.wojciechnowak.app

import org.apache.spark.sql.types.{StringType, StructField, StructType}

object Config {

  implicit val inputSchema: StructType = StructType(Array(
    StructField("c_left", StringType, nullable = false),
    StructField("c_right", StringType, nullable = false)
  ))
}
