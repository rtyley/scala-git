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

import net.lingala.zip4j.ZipFile
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

import java.io.File
import java.net.URL
import java.nio.file.{Files, Path}

package object test {
  def unpackRepo(zippedRepoResource: String): FileRepository = unpackRepo(pathForResource(zippedRepoResource))

  def unpackRepo(zippedRepo: Path): FileRepository = fileRepoFor(unpackRepoAndGetGitDir(zippedRepo))

  def pathForResource(fileName: String, clazz: Class[_] = getClass): Path = {
    val resource: URL = clazz.getResource(fileName)
    assert(resource != null, s"Resource for $fileName is null.")
    new File(resource.toURI).toPath
  }

  private def unpackRepoAndGetGitDir(zippedRepo: Path): File = {
    assert(Files.exists(zippedRepo), s"File $zippedRepo does not exist.")

    val repoParentFolder = Files.createTempDirectory(s"test-${zippedRepo.getFileName}-unpacked").toFile

    new ZipFile(zippedRepo.toFile.getAbsolutePath).extractAll(repoParentFolder.getAbsolutePath)

    repoParentFolder
  }

  private def fileRepoFor(resolvedGitDir: File): FileRepository = {
    require(resolvedGitDir.exists)
    FileRepositoryBuilder.create(resolvedGitDir).asInstanceOf[FileRepository]
  }
}
