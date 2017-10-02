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

package com.madgag.git.model

import com.madgag.git.bfg.model.{FileName, Tree}
import org.eclipse.jgit.lib.FileMode
import org.eclipse.jgit.lib.FileMode._
import org.eclipse.jgit.lib.ObjectId.zeroId
import org.scalatest.{FlatSpec, Matchers}

class TreeEntrySpec extends FlatSpec with Matchers {

  def a(mode: FileMode, name: String) = Tree.Entry(FileName(name), mode, zeroId)

  "Tree entry ordering" should "match ordering used by Git" in {
    a(TREE, "agit-test-utils") should be < a(TREE, "agit")
  }
}