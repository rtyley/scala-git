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

import org.eclipse.jgit.lib.{ObjectInserter, ObjectReader, ObjectDatabase}

class ThreadLocalObjectDatabaseResources(objectDatabase: ObjectDatabase) {
  private lazy val _reader = new ThreadLocal[ObjectReader] {
    override def initialValue() = objectDatabase.newReader()
  }

  private lazy val _inserter = new ThreadLocal[ObjectInserter] {
    override def initialValue() = objectDatabase.newInserter()
  }

  def reader() = _reader.get

  def inserter() = _inserter.get
}