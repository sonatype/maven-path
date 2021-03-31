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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Maven-2 {@link MavenPath} parser.
 *
 * @since ???
 */
public class Maven2PathParser
{
  public static final String SNAPSHOT_SUFFIX = "SNAPSHOT";

  /**
   * Matches {@literal maven-metadata.xml} and subordinates.
   */
  private static final Pattern mavenMetadataPattern = Pattern.compile(
      "^(?<prefix>.+)/(?<filename>maven-metadata\\.xml(\\.(?<subtype>.+))?)$"
  );

  /**
   * Matches artifact and subordinates.
   */
  private static final Pattern artifactPattern = Pattern.compile(
      "^(?<group>.+)/(?<artifact>[^/]+)/(?<version>[^/]+)/(?<filename>\\k<artifact>-\\k<version>(-(?<classifier>[^.]+))?\\.(?<extension>.+))$"
  );

  /**
   * Matches SNAPSHOT artifact and subordinates.
   */
  private static final Pattern snapshotArtifactPattern = Pattern.compile(
      "^(?<group>.+)/(?<artifact>[^/]+)/(?<bversion>(?<vprefix>[^/]+)-SNAPSHOT)/(?<filename>\\k<artifact>-(?<version>\\k<vprefix>-(?<vsuffix>(?<vtimestamp>\\d+\\.\\d+)-(?<vbuild>\\d+)))(-(?<classifier>[^.]+))?\\.(?<extension>.+))$",
      Pattern.CASE_INSENSITIVE
  );

  private Maven2PathParser() {
    // empty
  }

  @Nullable
  public static MavenPath parse(final String path) {
    requireNonNull(path);

    // skip invalid paths
    if (path.startsWith("/") || path.endsWith("/")) {
      return null;
    }

    Matcher match = match(mavenMetadataPattern, path);
    if (match != null) {
      String prefix = match.group("prefix");
      String filename = match.group("filename");
      String subtype = match.group("subtype");
      String version = null;
      String artifactId = null;
      String groupId = null;

      // use the last segment to _guess_ G/GA/GAV variants
      String[] segments = prefix.split("/");
      String lastSegment = segments[segments.length - 1];
      if (segments.length > 2 && lastSegment.endsWith(SNAPSHOT_SUFFIX)) {
        // if last segment contains SNAPSHOT; most likely a <group>/<artifact>/<version>/maven-metadata.xml path
        version = lastSegment;
        artifactId = segments[segments.length - 2];
        groupId = String.join(".", Arrays.copyOfRange(segments, 0, segments.length - 2));
      }
      else if (segments.length > 1 && (lastSegment.contains(".") || lastSegment.contains("-"))) {
        // if last segment contains tokens which are more likely to be in artifactId; most likely a <group>/<artifact>/maven-metadata.xml path
        artifactId = lastSegment;
        groupId = String.join(".", Arrays.copyOfRange(segments, 0, segments.length - 1));
      }
      else {
        // else assume <group>/maven-metadata.xml path
        groupId = prefix.replace('/', '.');
      }

      return new MavenMetadataPath(
          path,
          filename,
          prefix,
          groupId,
          artifactId,
          version,
          subtype
      );
    }

    match = match(artifactPattern, path);
    if (match != null) {
      return new ArtifactPath(
          path,
          match.group("filename"),
          match.group("group").replace('/', '.'),
          match.group("artifact"),
          match.group("version"),
          match.group("classifier"),
          match.group("extension")
      );
    }

    match = match(snapshotArtifactPattern, path);
    if (match != null) {
      return new SnapshotArtifactPath(
          path,
          match.group("filename"),
          match.group("group").replace('/', '.'),
          match.group("artifact"),
          match.group("bversion"),
          match.group("version"),
          match.group("vtimestamp"),
          match.group("vbuild"),
          match.group("classifier"),
          match.group("extension")
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
