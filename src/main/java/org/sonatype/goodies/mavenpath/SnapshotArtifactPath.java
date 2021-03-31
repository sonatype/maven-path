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
 * {@literal SNAPSHOT} and subordinate {@link ArtifactPath}.
 *
 * @since ???
 */
@Immutable
public class SnapshotArtifactPath
  extends ArtifactPath
{
  private static final long serialVersionUID = 1L;

  public static final String SNAPSHOT_SUFFIX = "SNAPSHOT";

  public static final String DASH_SNAPSHOT_SUFFIX = "-" + SNAPSHOT_SUFFIX;

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
                              final String extension)
  {
    super(path, fileName, groupId, artifactId, version, classifier, extension);
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
                              final String extension)
  {
    super(path, fileName, groupId, artifactId, version, classifier, extension);
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
        ", extension='" + extension + '\'' +
        '}';
  }
}
