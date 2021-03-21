package ai.wojciechnowak.app

import org.apache.spark.sql.SparkSession

object Env {

  val spark: SparkSession = SparkSession.builder()
    .appName("wn-scala-spark-sample")
    .config("spark.master", "local")
    .getOrCreate()
}
