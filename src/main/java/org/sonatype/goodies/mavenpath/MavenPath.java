/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maven path.
 *
 * @since ???
 * @see MavenMetadataPath
 * @see ArtifactPath
 * @see SnapshotArtifactPath
 */
public class MavenPath
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  protected final String path;

  protected final String fileName;

  protected MavenPath(final String path, final String fileName) {
    this.path = checkNotNull(path);
    this.fileName = checkNotNull(fileName);
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
}
