/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maven-2 {@link MavenPath} factory.
 *
 * @since ???
 */
public class Maven2PathFactory
{
  public static final String MAVEN_METADATA_FILENAME = "maven-metadata.xml";

  private Maven2PathFactory() {
    // empty
  }

  public static MavenMetadataPath createMavenMetadata(final String prefix, @Nullable final String subordinateType) {
    checkNotNull(prefix);

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
    checkNotNull(groupId);
    checkNotNull(artifactId);
    checkNotNull(version);
    checkNotNull(type);

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
    checkNotNull(groupId);
    checkNotNull(artifactId);
    checkNotNull(baseVersion);
    checkNotNull(version);
    checkNotNull(timestamp);
    checkNotNull(build);
    checkNotNull(type);

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
