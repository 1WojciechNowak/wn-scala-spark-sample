package ai.wojciechnowak.app

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

object InputReader {

  def readCsv(filename: String)(implicit schema: StructType): DataFrame = {
    Env.spark.read
      .format("csv")
      .option("header", "false")
      .option("multiLine", "true")
      .option("delimiter", ", ")
      .schema(schema)
      .load(s"src/main/resources/data/$filename.csv")
  }
}