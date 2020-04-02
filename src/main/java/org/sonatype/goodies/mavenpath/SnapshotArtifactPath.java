/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static java.util.Objects.requireNonNull;

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
    this.baseVersion = requireNonNull(baseVersion);
    this.timestamp = requireNonNull(timestamp);
    this.build = requireNonNull(build);
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
    return getClass().getSimpleName() + "{" +
        "groupId='" + groupId + '\'' +
        ", artifactId='" + artifactId + '\'' +
        ", version='" + version + '\'' +
        ", baseVersion='" + baseVersion + '\'' +
        ", timestamp='" + timestamp + '\'' +
        ", build='" + build + '\'' +
        ", classifier='" + classifier + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
