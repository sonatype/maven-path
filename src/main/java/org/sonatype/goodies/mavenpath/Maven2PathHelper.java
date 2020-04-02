/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;

/**
 * Maven-2 path helpers.
 *
 * @since ???
 */
public class Maven2PathHelper
{
  public static final String TYPE_POM = "pom";

  private Maven2PathHelper() {
    // empty
  }

  /**
   * Generate Maven2 path prefix from coordinates.
   */
  public static String pathPrefix(final String groupId, final String artifactId, final String version) {
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
    return String.format("%s/%s/%s/%s-%s.%s",
        groupId.replace('.', '/'),
        artifactId,
        version,
        artifactId,
        version,
        TYPE_POM
    );
  }

  /**
   * Generate Maven2 artifact filename from coordinates.
   */
  public static String artifactFilename(final String artifactId,
                                        final String version,
                                        @Nullable final String classifier,
                                        final String type)
  {
    StringBuilder buff = new StringBuilder();
    buff.append(artifactId).append('-').append(version);

    if (classifier != null) {
      buff.append('-').append(classifier);
    }

    buff.append('.').append(type);

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
}
