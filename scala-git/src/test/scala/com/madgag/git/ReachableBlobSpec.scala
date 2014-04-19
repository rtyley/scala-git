/*
 * Copyright (c) 2014 Roberto Tyley
 *
 * This file is part of 'scala-git'.
 *
 * scala-git is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * scala-git is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/ .
 */

package com.madgag.git

import org.specs2.mutable._
import com.madgag.git.test._


class ReachableBlobSpec extends Specification {

  implicit val repo = unpackRepo("/sample-repos/example.git.zip")

  "reachable blobs" should {
    "match expectations" in {
      implicit val (revWalk, reader) = repo.singleThreadedReaderTuple

      allBlobsReachableFrom(abbrId("475d") asRevCommit) mustEqual Set("d8d1", "34bd", "e69d", "c784", "d004").map(abbrId)
    }
  }
}