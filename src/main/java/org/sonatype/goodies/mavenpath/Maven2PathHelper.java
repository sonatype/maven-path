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
 * Maven-2 path helpers.
 *
 * @since ???
 */
public class Maven2PathHelper
{
  public static final String EXTENSION_POM = "pom";

  private Maven2PathHelper() {
    // empty
  }

  /**
   * Generate Maven2 artifact path from coordinates.
   */
  public static String artifactPath(final String groupId,
                                    final String artifactId,
                                    final String version,
                                    @Nullable final String classifier,
                                    final String extension)
  {
    requireNonNull(groupId);
    requireNonNull(artifactId);
    requireNonNull(version);
    // classifier is nullable
    requireNonNull(extension);

    StringBuilder buff = new StringBuilder();
    buff.append(groupId.replace('.', '/'))
        .append('/')
        .append(artifactId)
        .append('/')
        .append(version)
        .append('/')
        .append(artifactId)
        .append('-')
        .append(version);

    if (classifier != null) {
      buff.append('-')
          .append(classifier);
    }

    buff.append('.')
        .append(extension);

    return buff.toString();
  }

  /**
   * Generate Maven2 artifact path prefix from coordinates.
   */
  public static String artifactPathPrefix(final String groupId, final String artifactId, final String version) {
    return String.format("%s/%s/%s/%s-%s",
        groupId.replace('.', '/'),
        artifactId,
        version,
        artifactId,
        version
    );
  }

  /**
   * Generate Maven2 POM path from coordinates.
   */
  public static String pomPath(final String groupId, final String artifactId, final String version) {
    return artifactPath(groupId, artifactId, version, null, EXTENSION_POM);
  }

  /**
   * Generate Maven2 artifact filename from coordinates.
   */
  public static String artifactFilename(final String artifactId,
                                        final String version,
                                        @Nullable final String classifier,
                                        final String extension)
  {
    StringBuilder buff = new StringBuilder();
    buff.append(artifactId).append('-').append(version);

    if (classifier != null) {
      buff.append('-').append(classifier);
    }

    buff.append('.').append(extension);

    return buff.toString();
  }

  /**
   * Generate Maven2 artifact base-path from coordinates.
   */
  public static String artifactBasePath(final String groupId, final String artifactId, final String version) {
    return String.format("%s/%s/%s",
        groupId.replace('.', '/'),
        artifactId,
        version
    );
  }

  /**
   * Generate Maven2 maven-metadata path from coordinates.
   */
  public static String metadataPath(final String groupId,
                                    @Nullable final String artifactId,
                                    @Nullable final String subordinateType)
  {
    requireNonNull(groupId);
    // artifactId is nullable
    // subordinateType is nullable

    StringBuilder buff = new StringBuilder();
    buff.append(groupId.replace('.', '/'))
        .append('/');

    if (artifactId != null) {
      buff.append(artifactId)
          .append('/');
    }

    buff.append(MAVEN_METADATA_FILENAME);

    if (subordinateType != null) {
      buff.append('.').append(subordinateType);
    }

    return buff.toString();
  }
}
