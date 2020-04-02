/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static java.util.Objects.requireNonNull;

/**
 * {@literal maven-metadata.xml} and subordinate {@link MavenPath}.
 *
 * @since ???
 */
@Immutable
public class MavenMetadataPath
  extends MavenPath
{
  private static final long serialVersionUID = 1L;

  private final String prefix;

  @Nullable
  private final String groupId;

  @Nullable
  private final String artifactId;

  @Nullable
  private final String version;

  @Nullable
  private final String subordinateType;

  public MavenMetadataPath(final String path,
                           final String fileName,
                           final String prefix,
                           @Nullable final String groupId,
                           @Nullable final String artifactId,
                           @Nullable final String version,
                           @Nullable final String subordinateType)
  {
    super(path, fileName);
    this.prefix = requireNonNull(prefix);
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.subordinateType = subordinateType;
  }

  public MavenMetadataPath(final String path,
                           final String fileName,
                           final String prefix,
                           @Nullable final String subordinateType)
  {
    this(path, fileName, prefix, null, null, null, subordinateType);
  }

  public String getPrefix() {
    return prefix;
  }

  @Nullable
  public String getGroupId() {
    return groupId;
  }

  @Nullable
  public String getArtifactId() {
    return artifactId;
  }

  @Nullable
  public String getVersion() {
    return version;
  }

  @Nullable
  public String getSubordinateType() {
    return subordinateType;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "prefix='" + prefix + '\'' +
        ", groupId='" + groupId + '\'' +
        ", artifactId='" + artifactId + '\'' +
        ", version='" + version + '\'' +
        ", subordinateType='" + subordinateType + '\'' +
        '}';
  }
}
