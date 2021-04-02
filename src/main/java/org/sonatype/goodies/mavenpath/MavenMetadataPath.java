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
 * {@literal maven-metadata.xml} and subordinate {@link MavenPath}.
 *
 * @since ???
 */
@Immutable
public class MavenMetadataPath
  extends MavenPath
{
  public static final String MAVEN_METADATA_FILENAME = "maven-metadata.xml";

  public static final String MAVEN_METADATA_CONTENT_TYPE = "application/xml";

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
