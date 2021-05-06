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

import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.util.RawParseUtils

object FileName {

  object ImplicitConversions {
    import language.implicitConversions

    implicit def string2FileName(str: String): FileName = FileName(str)

    implicit def filename2String(fileName: FileName): String = fileName.string
  }

  def apply(name: String): FileName = {
    require(!name.contains('/'), "File names can not contain '/'.")
    new FileName(Constants.encode(name))
  }
}

class FileName(val bytes: Array[Byte]) {

  override def equals(that: Any): Boolean = that match {
    case that: FileName => (hashCode == that.hashCode) && java.util.Arrays.equals(bytes, that.bytes)
    case _ => false
  }

  override lazy val hashCode: Int = java.util.Arrays.hashCode(bytes)

  lazy val string: String = RawParseUtils.decode(bytes)

  override def toString = string

}