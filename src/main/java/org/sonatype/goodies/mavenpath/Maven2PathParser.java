/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maven-2 {@link MavenPath} parser.
 *
 * @since ???
 */
public class Maven2PathParser
{
  private static final Logger log = LoggerFactory.getLogger(Maven2PathParser.class);

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
      "^(?<group>.+)/(?<artifact>[^/]+)/(?<version>[^/]+)/(?<filename>\\k<artifact>-\\k<version>(-(?<classifier>[^.]+))?\\.(?<type>.+))$"
  );

  /**
   * Matches SNAPSHOT artifact and subordinates.
   */
  private static final Pattern snapshotArtifactPattern = Pattern.compile(
      "^(?<group>.+)/(?<artifact>[^/]+)/(?<bversion>(?<vprefix>[^/]+)-SNAPSHOT)/(?<filename>\\k<artifact>-(?<version>\\k<vprefix>-(?<vsuffix>(?<vtimestamp>\\d+\\.\\d+)-(?<vbuild>\\d+)))(-(?<classifier>[^.]+))?\\.(?<type>.+))$",
      Pattern.CASE_INSENSITIVE
  );

  private Maven2PathParser() {
    // empty
  }

  @Nullable
  public static MavenPath parse(final String path) {
    checkNotNull(path);
    log.trace("Parse: {}", path);

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
      if (segments.length > 2 && lastSegment.endsWith("SNAPSHOT")) {
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
          match.group("type")
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
          match.group("type")
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
