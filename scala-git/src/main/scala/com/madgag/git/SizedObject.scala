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

import org.eclipse.jgit.lib.ObjectId

case class SizedObject(objectId: ObjectId, size: Long) extends Ordered[SizedObject] {
  def compare(that: SizedObject) = size.compareTo(that.size)
}