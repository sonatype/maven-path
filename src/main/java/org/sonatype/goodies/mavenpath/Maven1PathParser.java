/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Maven-1 {@link MavenPath} parser.
 *
 * @since ???
 */
public class Maven1PathParser
{
  private static final Logger log = LoggerFactory.getLogger(Maven1PathParser.class);

  // SEE: https://github.com/apache/maven-indexer/blob/master/indexer-core/src/main/java/org/apache/maven/index/artifact/M1GavCalculator.java
  // SEE: https://cwiki.apache.org/confluence/display/MAVENOLD/Repository+Layout+-+Final

  private static final Pattern artifactPattern = Pattern.compile(
      "^(?<group>[^/]+)/(?<types>[^/]+s)/(?<filename>(?<artifact>\\D+|[a-z0-9-_]+)-(?<version>\\d.+)\\.(?<type>\\D+(\\.md5|\\.sha1)?))$"
  );

  private Maven1PathParser() {
    // empty
  }

  @Nullable
  public static MavenPath parse(final String path) {
    requireNonNull(path);
    log.trace("Parse: {}", path);

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
      String type = match.group("type");

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
            type
        );
      }

      return new ArtifactPath(
          path,
          filename,
          group,
          artifact,
          version,
          classifier,
          type
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
