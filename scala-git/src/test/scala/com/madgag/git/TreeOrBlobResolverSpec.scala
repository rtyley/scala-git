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
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.ObjectReader
import org.eclipse.jgit.revwalk.RevWalk
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should._

class TreeOrBlobResolverSpec extends AnyFlatSpec with Matchers with EitherValues {

  implicit val repo: FileRepository = unpackRepo("/sample-repos/annotatedTagExample.git.zip")

  "annotated tag" should "be correctly evaluated, not null" in {
    implicit val (revWalk: RevWalk, reader: ObjectReader) = repo.singleThreadedReaderTuple

    val annotatedTag = repo.resolve("chapter1").asRevTag

    treeOrBlobPointedToBy(annotatedTag).value shouldBe abbrId("4c6a")
  }
}
