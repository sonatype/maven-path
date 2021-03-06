/*
 * Copyright (c) 2020-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.goodies.mavenpath;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import static java.util.Objects.requireNonNull;

/**
 * Maven path.
 *
 * @since ???
 * @see MavenMetadataPath
 * @see ArtifactPath
 * @see SnapshotArtifactPath
 */
@Immutable
public class MavenPath
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  protected final String path;

  protected final String fileName;

  protected MavenPath(final String path, final String fileName) {
    this.path = requireNonNull(path);
    this.fileName = requireNonNull(fileName);
  }

  public String getPath() {
    return path;
  }

  public String getFileName() {
    return fileName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MavenPath)) {
      return false;
    }
    MavenPath mavenPath = (MavenPath) o;
    return Objects.equals(path, mavenPath.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "path='" + path + '\'' +
        ", fileName='" + fileName + '\'' +
        '}';
  }

  //
  // Helpers
  //

  public static boolean isSubordinate(final MavenPath path) {
    boolean subordinate = false;
    if (path instanceof ArtifactPath) {
      subordinate = ((ArtifactPath)path).isSubordinate();
    }
    else if (path instanceof MavenMetadataPath) {
      subordinate = ((MavenMetadataPath)path).getSubordinateType() != null;
    }
    return subordinate;
  }
}
