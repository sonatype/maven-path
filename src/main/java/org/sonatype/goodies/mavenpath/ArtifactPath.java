/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

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

  protected final String type;

  public ArtifactPath(final String path,
                      final String fileName,
                      final String groupId,
                      final String artifactId,
                      final String version,
                      @Nullable final String classifier,
                      final String type)
  {
    super(path, fileName);
    this.groupId = checkNotNull(groupId);
    this.artifactId = checkNotNull(artifactId);
    this.version = checkNotNull(version);
    this.classifier = classifier;
    this.type = checkNotNull(type);
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

  public String getType() {
    return type;
  }

  public boolean isSubordinate() {
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

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("groupId", groupId)
        .add("artifactId", artifactId)
        .add("version", version)
        .add("classifier", classifier)
        .add("type", type)
        .toString();
  }

  //
  // Helpers
  //

  public static boolean isSnapshotVersion(final ArtifactPath path) {
    if (path instanceof SnapshotArtifactPath) {
      return true;
    }
    String version = path.getVersion();
    return version != null && version.endsWith("-SNAPSHOT");
  }
}
