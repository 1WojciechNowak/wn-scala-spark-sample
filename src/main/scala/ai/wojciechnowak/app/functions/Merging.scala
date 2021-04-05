package ai.wojciechnowak.app.functions

import ai.wojciechnowak.app.Env
import ai.wojciechnowak.app.model.IPNumber.NUMBER
import ai.wojciechnowak.app.model.Scope.{END, SCOPE, START}
import ai.wojciechnowak.app.model.{IPNumber, Scope}
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{collect_list, explode, udf}

trait Merging {

  import Env.spark.implicits._
  val merge: Dataset[IPNumber] => Dataset[Scope[IPNumber]] = { ipNumbers =>
    ipNumbers.agg(unTypedMerge(collect_list(NUMBER)) as Scope.NAME)
      .withColumn(Scope.NAME, explode(SCOPE))
      .select(START, END)
      .as[Scope[IPNumber]]
  }

  /**
    * based on:
    * https://stackoverflow.com/questions/47611842/combine-multiple-sequential-entries-in-scala-spark
    */
  private val unTypedMerge: UserDefinedFunction = udf((setOfIPNumbers: List[Long] ) => {
    val ints = setOfIPNumbers.reverse
    ints.drop(1)
      .foldLeft(List(List(ints.head)))((acc, e) => {
        if ((acc.head.head - e) <= 1) (e :: acc.head) :: acc.tail
        else List(e) :: acc
      })
      .map(group => if (group.size > 1) Scope(IPNumber(group.min), IPNumber(group.max)) else Scope(IPNumber(group.head)))
  })

  /**
    * Own implementation of unTypedMergeFunction
    * not used, because spark throws serialization related exception
    */
  protected def toScopes(setOfIPNumbers: List[IPNumber]): List[Scope[IPNumber]] = {
    def toScopesHelper(ipNumbers: List[IPNumber], buffer: List[Scope[IPNumber]], newScopeStart: Option[IPNumber]): List[Scope[IPNumber]] = {
      (ipNumbers, newScopeStart) match {
        case (first :: _ :: _, None) => toScopesHelper(ipNumbers, buffer, Some(first))
        case (first :: second :: tail, _) if first.getNext == second => toScopesHelper(second :: tail, buffer, newScopeStart)
        case (first :: second :: tail, Some(start)) => toScopesHelper(second :: tail, buffer :+ Scope(start, first), Some(second))
        case (first :: Nil, Some(start)) => buffer :+ Scope(start, first)
        case (first :: Nil, None) => buffer :+ Scope(first)
        case (Nil, _) => buffer
      }
    }

    toScopesHelper(setOfIPNumbers, List.empty, None)
  }
}
