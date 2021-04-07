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

  def readTable(tableName: String): DataFrame = {
    Env.spark.read
      .format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://db:5432/postgres")
      .option("user", "postgres")
      .option("password", "password")
      .option("dbtable", s"public.$tableName")
      .load()
  }
}