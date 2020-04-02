/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
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
                                            final String type)
  {
    requireNonNull(groupId);
    requireNonNull(artifactId);
    requireNonNull(version);
    requireNonNull(type);

    StringBuilder buff = new StringBuilder();
    buff.append(artifactId);
    buff.append('-').append(version);
    if (classifier != null) {
      buff.append('-').append(classifier);
    }
    buff.append('.').append(type);
    String fileName = buff.toString();

    buff.insert(0, '/').insert(0, version);
    buff.insert(0, '/').insert(0, artifactId);
    buff.insert(0, '/').insert(0, groupId.replace('.', '/'));
    String path = buff.toString();

    return new ArtifactPath(path, fileName, groupId, artifactId, version, classifier, type);
  }

  public static SnapshotArtifactPath createSnapshotArtifact(final String groupId,
                                                            final String artifactId,
                                                            final String baseVersion,
                                                            final String version,
                                                            final String timestamp,
                                                            final String build,
                                                            @Nullable final String classifier,
                                                            final String type)
  {
    requireNonNull(groupId);
    requireNonNull(artifactId);
    requireNonNull(baseVersion);
    requireNonNull(version);
    requireNonNull(timestamp);
    requireNonNull(build);
    requireNonNull(type);

    StringBuilder buff = new StringBuilder();
    buff.append(artifactId);
    buff.append('-').append(version);
    if (classifier != null) {
      buff.append('-').append(classifier);
    }
    buff.append('.').append(type);
    String fileName = buff.toString();

    buff.insert(0, '/').insert(0, baseVersion);
    buff.insert(0, '/').insert(0, artifactId);
    buff.insert(0, '/').insert(0, groupId.replace('.', '/'));
    String path = buff.toString();

    return new SnapshotArtifactPath(path, fileName, groupId, artifactId, baseVersion, version, timestamp, build, classifier, type);
  }
}
