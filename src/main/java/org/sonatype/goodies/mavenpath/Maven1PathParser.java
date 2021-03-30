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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Maven-1 {@link MavenPath} parser.
 *
 * @since ???
 */
public class Maven1PathParser
{
  // SEE: https://github.com/apache/maven-indexer/blob/master/indexer-core/src/main/java/org/apache/maven/index/artifact/M1GavCalculator.java
  // SEE: https://cwiki.apache.org/confluence/display/MAVENOLD/Repository+Layout+-+Final

  private static final Pattern artifactPattern = Pattern.compile(
      "^(?<group>[^/]+)/(?<types>[^/]+s)/(?<filename>(?<artifact>\\D+|[a-z0-9-_]+)-(?<version>\\d.+)\\.(?<extension>\\D+(\\.md5|\\.sha1)?))$"
  );

  private Maven1PathParser() {
    // empty
  }

  @Nullable
  public static MavenPath parse(final String path) {
    requireNonNull(path);

    // skip invalid paths
    if (path.startsWith("/") || path.endsWith("/")) {
      return null;
    }

    Matcher match = match(artifactPattern, path);
    if (match != null) {
      String group = match.group("group");
      String types = match.group("types");
      String filename = match.group("filename");
      String artifact = match.group("artifact");
      String version = match.group("version");
      String extension = match.group("extension");

      // special handling for a few types which have classifiers
      String classifier = null;
      if (types.equals("java-sources")) {
        classifier = "sources";
      }
      else if (types.equals("javadocs")) {
        classifier = "javadoc";
      }
      else if (types.equals("ejbs") && (filename.endsWith("client.jar") ||
          filename.endsWith("client.jar.sha1") ||
          filename.endsWith("client.jar.md5"))) {
        classifier = "client";
      }

      // if classifier was detected, strip it off from version
      if (classifier != null) {
        version = version.substring(0, version.length() - (classifier.length() + 1));
      }

      if (version.endsWith("SNAPSHOT")) {
        return new SnapshotArtifactPath(
            path,
            filename,
            group,
            artifact,
            version,
            classifier,
            extension
        );
      }

      return new ArtifactPath(
          path,
          filename,
          group,
          artifact,
          version,
          classifier,
          extension
      );
    }

    return null;
  }

  @Nullable
  private static Matcher match(final Pattern pattern, final String value) {
    Matcher matcher = pattern.matcher(value);
    if (matcher.matches()) {
      return matcher;
    }
    return null;
  }
}
