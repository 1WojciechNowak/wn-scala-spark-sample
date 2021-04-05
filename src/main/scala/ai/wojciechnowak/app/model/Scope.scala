package ai.wojciechnowak.app.model

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.col

case class Scope[T](start: T, end: T)
object Scope {
  def apply[T](single: T): Scope[T] = new Scope(single, single)

  val START: Column = col("scope.start")
  val END: Column = col("scope.end")
  val SCOPE: Column = col("scope")

  val NAME = "scope"
}

