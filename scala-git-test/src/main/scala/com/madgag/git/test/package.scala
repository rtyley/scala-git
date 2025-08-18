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

import java.io.File
import java.io.File.separatorChar
import java.net.URL
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

import java.nio.file.Files.createTempDirectory

package object test {
  def unpackRepo(fileName: String): FileRepository = {
    val resolvedGitDir = unpackRepoAndGetGitDir(fileName)
    require(resolvedGitDir.exists)
    FileRepositoryBuilder.create(resolvedGitDir).asInstanceOf[FileRepository]
  }

  def unpackRepoAndGetGitDir(fileName: String): File = {
    val resource: URL = getClass.getResource(fileName)
    assert(resource != null, s"Resource for $fileName is null.")

    val file = new File(resource.toURI)
    assert(file.exists(), s"File $file does not exist.")

    val repoParentFolder = new File(createTempDirectory("test").toFile, fileName.replace(separatorChar, '_') + "-unpacked")
    repoParentFolder.mkdir()

    new ZipFile(file.getAbsolutePath).extractAll(repoParentFolder.getAbsolutePath)

    repoParentFolder
  }
}
