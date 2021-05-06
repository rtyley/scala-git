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

package com.madgag.git

import com.madgag.git.test._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should._
import com.madgag.scala.collection.decorators._
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.ObjectReader
import org.eclipse.jgit.revwalk.RevWalk

import scala.collection.mutable

class RichTreeWalkSpec extends AnyFlatSpec with Matchers {

   implicit val repo: FileRepository = unpackRepo("/sample-repos/example.git.zip")

   "rich tree" should "implement exists" in {
     implicit val (revWalk: RevWalk, reader: ObjectReader) = repo.singleThreadedReaderTuple

     val tree = abbrId("830e").asRevTree

     tree.walk().exists(_.getNameString == "one-kb-random") shouldBe true
     tree.walk().exists(_.getNameString == "chimera") shouldBe false
   }

   it should "implement map" in {
     implicit val (revWalk: RevWalk, reader: ObjectReader) = repo.singleThreadedReaderTuple

     val tree = abbrId("830e").asRevTree

     val fileNameList = tree.walk().map(_.getNameString).toList

     fileNameList should have size 6

     fileNameList.groupUp(identity)(_.size) should contain ("zero" -> 2)
   }

   it should "implement withFilter" in {
     implicit val (revWalk: RevWalk, reader: ObjectReader) = repo.singleThreadedReaderTuple

     val tree = abbrId("830e").asRevTree

     val filteredTreeWalk = tree.walk().withFilter(_.getNameString != "zero")

     val filenames = filteredTreeWalk.map(_.getNameString).toList

     filenames should have size 4

     filenames should not contain "zero"
   }

   it should "implement foreach" in {
     implicit val (revWalk: RevWalk, reader: ObjectReader) = repo.singleThreadedReaderTuple

     val tree = abbrId("830e").asRevTree

     val fileNames = mutable.Buffer[String]()

     tree.walk().foreach(tw => fileNames += tw.getNameString)

     fileNames.toList should have size 6
   }

   it should "work with for comprehensions" in {
     implicit val (revWalk: RevWalk, reader: ObjectReader) = repo.singleThreadedReaderTuple

     val tree = abbrId("830e").asRevTree

     for (t <- tree.walk()) yield t.getNameString

     for (t <- tree.walk()) { t.getNameString }

     for (t <- tree.walk() if t.getNameString == "zero") { t.getDepth }

   }
 }