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
 * {@literal SNAPSHOT} and subordinate {@link ArtifactPath}.
 *
 * @since ???
 */
@Immutable
public class SnapshotArtifactPath
  extends ArtifactPath
{
  private static final long serialVersionUID = 1L;

  private final String baseVersion;

  // TODO: Consider supporting parsing out timestamp?

  @Nullable
  private final String timestamp;

  @Nullable
  private final String build;

  /**
   * Maven-2 constructor.
   */
  public SnapshotArtifactPath(final String path,
                              final String fileName,
                              final String groupId,
                              final String artifactId,
                              final String baseVersion,
                              final String version,
                              final String timestamp,
                              final String build,
                              @Nullable final String classifier,
                              final String type)
  {
    super(path, fileName, groupId, artifactId, version, classifier, type);
    this.baseVersion = checkNotNull(baseVersion);
    this.timestamp = checkNotNull(timestamp);
    this.build = checkNotNull(build);
  }

  /**
   * Maven-1 constructor.
   */
  public SnapshotArtifactPath(final String path,
                              final String fileName,
                              final String groupId,
                              final String artifactId,
                              final String version,
                              @Nullable final String classifier,
                              final String type)
  {
    super(path, fileName, groupId, artifactId, version, classifier, type);
    this.baseVersion = version;
    this.timestamp = null;
    this.build = null;
  }

  public String getBaseVersion() {
    return baseVersion;
  }

  @Nullable
  public String getTimestamp() {
    return timestamp;
  }

  @Nullable
  public String getBuild() {
    return build;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("groupId", groupId)
        .add("artifactId", artifactId)
        .add("baseVersion", baseVersion)
        .add("version", version)
        .add("timestamp", timestamp)
        .add("build", build)
        .add("classifier", classifier)
        .add("type", type)
        .toString();
  }
}
