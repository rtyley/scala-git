package com.madgag.diff

import com.madgag.scala.collection.decorators.MapDecorator

object MapDiff {
  def apply[K,V](before: Map[K,V], after: Map[K,V]): MapDiff[K,V] =
    MapDiff(Map(Before -> before, After -> after))
}

case class MapDiff[K, V](beforeAndAfter: Map[BeforeAndAfter, Map[K,V]]) {

  lazy val commonElements: Set[K] = beforeAndAfter.values.map(_.keySet).reduce(_ intersect _)

  lazy val only: Map[BeforeAndAfter, Map[K,V]] =
    beforeAndAfter.mapV(_.view.filterKeys(!commonElements(_)).toMap)

  lazy val (unchanged, changed) =
    commonElements.partition(k => beforeAndAfter(Before)(k) == beforeAndAfter(After)(k))

  lazy val unchangedMap: Map[K,V] = beforeAndAfter(Before).view.filterKeys(unchanged).toMap

  lazy val changedMap: Map[K,Map[BeforeAndAfter, V]] =
    changed.map(k => k -> beforeAndAfter.mapV(_(k)).toMap).toMap

}