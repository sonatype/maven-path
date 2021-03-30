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

import static java.util.Objects.requireNonNull;
import static org.sonatype.goodies.mavenpath.MavenMetadataPath.MAVEN_METADATA_FILENAME;

/**
 * Maven-2 {@link MavenPath} factory.
 *
 * @since ???
 */
public class Maven2PathFactory
{
  private Maven2PathFactory() {
    // empty
  }

  public static MavenMetadataPath createMavenMetadata(final String prefix, @Nullable final String subordinateType) {
    requireNonNull(prefix);

    StringBuilder buff = new StringBuilder();
    buff.append(MAVEN_METADATA_FILENAME);
    if (subordinateType != null) {
      buff.append('.').append(subordinateType);
    }
    String fileName = buff.toString();

    buff.insert(0, '/');
    buff.insert(0, prefix);
    String path = buff.toString();

    return new MavenMetadataPath(path, fileName, prefix, subordinateType);
  }

  public static ArtifactPath createArtifact(final String groupId,
                                            final String artifactId,
                                            final String version,
                                            @Nullable final String classifier,
                                            final String extension)
  {
    requireNonNull(groupId);
    requireNonNull(artifactId);
    requireNonNull(version);
    requireNonNull(extension);

    StringBuilder buff = new StringBuilder();
    buff.append(artifactId);
    buff.append('-').append(version);
    if (classifier != null) {
      buff.append('-').append(classifier);
    }
    buff.append('.').append(extension);
    String fileName = buff.toString();

    buff.insert(0, '/').insert(0, version);
    buff.insert(0, '/').insert(0, artifactId);
    buff.insert(0, '/').insert(0, groupId.replace('.', '/'));
    String path = buff.toString();

    return new ArtifactPath(path, fileName, groupId, artifactId, version, classifier, extension);
  }

  public static SnapshotArtifactPath createSnapshotArtifact(final String groupId,
                                                            final String artifactId,
                                                            final String baseVersion,
                                                            final String version,
                                                            final String timestamp,
                                                            final String build,
                                                            @Nullable final String classifier,
                                                            final String extension)
  {
    requireNonNull(groupId);
    requireNonNull(artifactId);
    requireNonNull(baseVersion);
    requireNonNull(version);
    requireNonNull(timestamp);
    requireNonNull(build);
    requireNonNull(extension);

    StringBuilder buff = new StringBuilder();
    buff.append(artifactId);
    buff.append('-').append(version);
    if (classifier != null) {
      buff.append('-').append(classifier);
    }
    buff.append('.').append(extension);
    String fileName = buff.toString();

    buff.insert(0, '/').insert(0, baseVersion);
    buff.insert(0, '/').insert(0, artifactId);
    buff.insert(0, '/').insert(0, groupId.replace('.', '/'));
    String path = buff.toString();

    return new SnapshotArtifactPath(path, fileName, groupId, artifactId, baseVersion, version, timestamp, build, classifier, extension);
  }
}
