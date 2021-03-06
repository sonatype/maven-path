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

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static java.util.Objects.requireNonNull;

/**
 * Artifact and subordinate {@link MavenPath}.
 *
 * @since ???
 */
@Immutable
public class ArtifactPath
  extends MavenPath
{
  private static final long serialVersionUID = 1L;

  protected final String groupId;

  protected final String artifactId;

  protected final String version;

  @Nullable
  protected final String classifier;

  protected final String extension;

  public ArtifactPath(final String path,
                      final String fileName,
                      final String groupId,
                      final String artifactId,
                      final String version,
                      @Nullable final String classifier,
                      final String extension)
  {
    super(path, fileName);
    this.groupId = requireNonNull(groupId);
    this.artifactId = requireNonNull(artifactId);
    this.version = requireNonNull(version);
    this.classifier = classifier;
    this.extension = requireNonNull(extension);
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }

  @Nullable
  public String getClassifier() {
    return classifier;
  }

  public String getExtension() {
    return extension;
  }

  public boolean isSubordinate() {
    return isSubordinate(fileName);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "groupId='" + groupId + '\'' +
        ", artifactId='" + artifactId + '\'' +
        ", version='" + version + '\'' +
        ", classifier='" + classifier + '\'' +
        ", extension='" + extension + '\'' +
        '}';
  }

  //
  // Helpers
  //

  public static boolean isSubordinate(final String fileName) {
    requireNonNull(fileName);
    for (ChecksumType checksum : ChecksumType.values()) {
      if (checksum.pathMatches(fileName)) {
        return true;
      }
    }
    for (SignatureType signature : SignatureType.values()) {
      if (signature.pathMatches(fileName)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isSnapshotVersion(final ArtifactPath path) {
    requireNonNull(path);
    if (path instanceof SnapshotArtifactPath) {
      return true;
    }
    String version = path.getVersion();
    return version != null && version.endsWith(SnapshotArtifactPath.DASH_SNAPSHOT_SUFFIX);
  }
}
