package ai.wojciechnowak.app

import org.apache.spark.sql.types.{StringType, StructField, StructType}

object Config {

  implicit val inputSchema: StructType = StructType(Array(
    StructField("left", StringType, nullable = false),
    StructField("right", StringType, nullable = false)
  ))
}
