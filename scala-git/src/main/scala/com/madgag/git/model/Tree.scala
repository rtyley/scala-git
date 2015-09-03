/*
 * Copyright 2015 Roberto Tyley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.madgag.git.bfg.model

import org.eclipse.jgit.lib._
import org.eclipse.jgit.treewalk.CanonicalTreeParser

import Constants.{OBJ_BLOB, OBJ_TREE}
import scala.collection
import org.eclipse.jgit.lib.FileMode.TREE

object Tree {

  val Empty = Tree(Map.empty[FileName, (FileMode, ObjectId)])

  def apply(entries: Traversable[Tree.Entry]): Tree = Tree(entries.map {
    entry => entry.name -> ((entry.fileMode, entry.objectId))
  }.toMap)

  def apply(objectId: ObjectId)(implicit reader: ObjectReader): Tree = Tree(entriesFor(objectId))

  def entriesFor(objectId: ObjectId)(implicit reader: ObjectReader): Seq[Entry] = {
    val treeParser = new CanonicalTreeParser
    treeParser.reset(reader, objectId)
    val entries = collection.mutable.Buffer[Entry]()
    while (!treeParser.eof) {
      entries += Entry(treeParser)
      treeParser.next()
    }
    entries
  }

  // def apply(objectId: ObjectId)(implicit objectReader: ObjectReader) = Tree(entriesFor(objectId))

  case class Entry(name: FileName, fileMode: FileMode, objectId: ObjectId) extends Ordered[Entry] {

    def compare(that: Entry) = pathCompare(name.bytes, that.name.bytes, fileMode, that.fileMode)

    private def pathCompare(a: Array[Byte], b: Array[Byte], aMode: FileMode, bMode: FileMode): Int = {

      def lastPathChar(mode: FileMode): Int = if (mode == TREE) '/' else '\u0000'

      var pos = 0
      while (pos < a.length && pos < b.length) {
        val cmp: Int = (a(pos) & 0xff) - (b(pos) & 0xff)
        if (cmp != 0) return cmp
        pos += 1
      }

      if (pos < a.length) {
        (a(pos) & 0xff) - lastPathChar(bMode)
      } else if (pos < b.length) {
        lastPathChar(aMode) - (b(pos) & 0xff)
      } else {
        0
      }
    }
  }

  object Entry {

    def apply(treeParser: CanonicalTreeParser): Entry = {
      val nameBuff = new Array[Byte](treeParser.getNameLength)
      treeParser.getName(nameBuff, 0)

      Entry(new FileName(nameBuff), treeParser.getEntryFileMode, treeParser.getEntryObjectId)
    }

  }

  trait EntryGrouping {
    val treeEntries: Traversable[Tree.Entry]
  }

}

case class Tree(entryMap: Map[FileName, (FileMode, ObjectId)]) {

  protected def repr = this

  lazy val entries = entryMap.map {
    case (name, (fileMode, objectId)) => Tree.Entry(name, fileMode, objectId)
  }

  lazy val entriesByType = entries.groupBy(_.fileMode.getObjectType).withDefaultValue(Seq.empty)

  lazy val sortedEntries = entries.toList.sorted

  def formatter: TreeFormatter = {
    val treeFormatter = new TreeFormatter()
    sortedEntries.foreach(e => treeFormatter.append(e.name.bytes, e.fileMode, e.objectId))

    treeFormatter
  }

  lazy val objectId = formatter.computeId(new ObjectInserter.Formatter)

  lazy val blobs = TreeBlobs(entriesByType(OBJ_BLOB).flatMap {
    e => BlobFileMode(e.fileMode).map {
      blobFileMode => e.name -> ((blobFileMode, e.objectId))
    }
  }.toMap)

  lazy val subtrees = TreeSubtrees(entriesByType(OBJ_TREE).map {
    e => e.name -> e.objectId
  }.toMap)

  def copyWith(subtrees: TreeSubtrees, blobs: TreeBlobs): Tree = {
    val otherEntries = (entriesByType - OBJ_BLOB - OBJ_TREE).values.flatten
    Tree(blobs.treeEntries ++ subtrees.treeEntries ++ otherEntries)
  }

}

case class TreeBlobEntry(filename: FileName, mode: BlobFileMode, objectId: ObjectId) {
  lazy val toTreeEntry = Tree.Entry(filename, mode.mode, objectId)

  lazy val withoutName: (BlobFileMode, ObjectId) = (mode, objectId)
}

object TreeBlobs {
  import language.implicitConversions

  implicit def entries2Object(entries: Traversable[TreeBlobEntry]) = TreeBlobs(entries)

  def apply(entries: Traversable[TreeBlobEntry]): TreeBlobs =
    TreeBlobs(entries.map(e => e.filename -> ((e.mode, e.objectId))).toMap)
}

case class TreeBlobs(entryMap: Map[FileName, (BlobFileMode, ObjectId)]) extends Tree.EntryGrouping {

  lazy val entries = entryMap.map {
    case (name, (blobFileMode, objectId)) => TreeBlobEntry(name, blobFileMode, objectId)
  }

  lazy val treeEntries = entries.map(_.toTreeEntry)

  def objectId(fileName: FileName) = entryMap.get(fileName).map(_._2)

}

case class TreeSubtrees(entryMap: Map[FileName, ObjectId]) extends Tree.EntryGrouping {

  lazy val treeEntries = entryMap.map {
    case (name, objectId) => Tree.Entry(name, TREE, objectId)
  }

  lazy val withoutEmptyTrees = TreeSubtrees(entryMap.filterNot(_._2 == Tree.Empty.objectId))
}