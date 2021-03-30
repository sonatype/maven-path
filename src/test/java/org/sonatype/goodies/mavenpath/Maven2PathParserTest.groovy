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
package org.sonatype.goodies.mavenpath

import org.junit.Test
import spock.lang.Specification

/**
 * {@link Maven2PathParser} tests.
 */
class Maven2PathParserTest
    extends Specification
{
  private static boolean mavenMetadataMatches(final String path,
                                              final String fileName,
                                              final String prefix,
                                              final String groupId,
                                              final String artifactId,
                                              final String version,
                                              final String subordinateType)
  {
    MavenPath parsed = Maven2PathParser.parse(path)
    if (parsed == null || parsed.path != path || parsed.fileName != fileName) {
      return false
    }
    if (parsed instanceof MavenMetadataPath) {
      return parsed.prefix == prefix &&
          parsed.groupId == groupId &&
          parsed.artifactId == artifactId &&
          parsed.version == version &&
          parsed.subordinateType == subordinateType
    }
    return false
  }

  @Test
  void 'parse maven-metadata'() {
    expect:
      mavenMetadataMatches(path, fileName, prefix, groupId, artifactId, version, subordinateType) == result
    where:
      // @formatter:off
      path                                                           | fileName                  | prefix                                 | groupId                    | artifactId    | version        | subordinateType || result
      'foo/bar/baz/maven-metadata.xml'                               | 'maven-metadata.xml'      | 'foo/bar/baz'                          | 'foo.bar.baz'              | null          | null           | null            || true
      'foo/bar/baz/maven-metadata.xml.sha1'                          | 'maven-metadata.xml.sha1' | 'foo/bar/baz'                          | 'foo.bar.baz'              | null          | null           | 'sha1'          || true
      'foo/bar/baz/maven-metadata.xml.asc'                           | 'maven-metadata.xml.asc'  | 'foo/bar/baz'                          | 'foo.bar.baz'              | null          | null           | 'asc'           || true
      'foo/bar/baz/maven-metadata.xml.info'                          | 'maven-metadata.xml.info' | 'foo/bar/baz'                          | 'foo.bar.baz'              | null          | null           | 'info'          || true
      'foo/bar/baz/1.0-SNAPSHOT/maven-metadata.xml'                  | 'maven-metadata.xml'      | 'foo/bar/baz/1.0-SNAPSHOT'             | 'foo.bar'                  | 'baz'         | '1.0-SNAPSHOT' | null            || true
      'foo/bar/baz/1.0-SNAPSHOT/maven-metadata.xml.sha1'             | 'maven-metadata.xml.sha1' | 'foo/bar/baz/1.0-SNAPSHOT'             | 'foo.bar'                  | 'baz'         | '1.0-SNAPSHOT' | 'sha1'          || true
      'foo/bar/1.0-SNAPSHOT/maven-metadata.xml'                      | 'maven-metadata.xml'      | 'foo/bar/1.0-SNAPSHOT'                 | 'foo'                      | 'bar'         | '1.0-SNAPSHOT' | null            || true
      'foo/bar/1.0-SNAPSHOT/maven-metadata.xml.sha1'                 | 'maven-metadata.xml.sha1' | 'foo/bar/1.0-SNAPSHOT'                 | 'foo'                      | 'bar'         | '1.0-SNAPSHOT' | 'sha1'          || true
      'foo/bar/baz-qux/maven-metadata.xml'                           | 'maven-metadata.xml'      | 'foo/bar/baz-qux'                      | 'foo.bar'                  | 'baz-qux'     | null           | null            || true
      'foo/bar/baz-qux/maven-metadata.xml.sha1'                      | 'maven-metadata.xml.sha1' | 'foo/bar/baz-qux'                      | 'foo.bar'                  | 'baz-qux'     | null           | 'sha1'          || true
      'foo/bar/baz.qux/maven-metadata.xml'                           | 'maven-metadata.xml'      | 'foo/bar/baz.qux'                      | 'foo.bar'                  | 'baz.qux'     | null           | null            || true
      'foo/bar/baz.qux/maven-metadata.xml.sha1'                      | 'maven-metadata.xml.sha1' | 'foo/bar/baz.qux'                      | 'foo.bar'                  | 'baz.qux'     | null           | 'sha1'          || true
      'commons-cli/commons-cli/maven-metadata.xml'                   | 'maven-metadata.xml'      | 'commons-cli/commons-cli'              | 'commons-cli'              | 'commons-cli' | null           | null            || true
      'commons-cli/commons-cli/maven-metadata.xml.sha1'              | 'maven-metadata.xml.sha1' | 'commons-cli/commons-cli'              | 'commons-cli'              | 'commons-cli' | null           | 'sha1'          || true
      'commons-cli/commons-cli/1.0-SNAPSHOT/maven-metadata.xml'      | 'maven-metadata.xml'      | 'commons-cli/commons-cli/1.0-SNAPSHOT' | 'commons-cli'              | 'commons-cli' | '1.0-SNAPSHOT' | null            || true
      'commons-cli/commons-cli/1.0-SNAPSHOT/maven-metadata.xml.sha1' | 'maven-metadata.xml.sha1' | 'commons-cli/commons-cli/1.0-SNAPSHOT' | 'commons-cli'              | 'commons-cli' | '1.0-SNAPSHOT' | 'sha1'          || true
      'org/apache/maven/plugins/maven-metadata.xml'                  | 'maven-metadata.xml'      | 'org/apache/maven/plugins'             | 'org.apache.maven.plugins' | null          | null           | null            || true
      'org/apache/maven/plugins/maven-metadata.xml.sha1'             | 'maven-metadata.xml.sha1' | 'org/apache/maven/plugins'             | 'org.apache.maven.plugins' | null          | null           | 'sha1'          || true
      // @formatter:on
  }

  private boolean artifactMatches(final String path,
                                  final String fileName,
                                  final String groupId,
                                  final String artifactId,
                                  final String version,
                                  final String classifier,
                                  final String extension)
  {
    MavenPath parsed = Maven2PathParser.parse(path)
    if (parsed == null || parsed.path != path || parsed.fileName != fileName) {
      return false
    }
    if (parsed instanceof ArtifactPath) {
      return parsed.groupId == groupId &&
          parsed.artifactId == artifactId &&
          parsed.version == version &&
          parsed.classifier == classifier &&
          parsed.extension == extension
    }
    return false
  }

  @Test
  void 'parse artifact'() {
    expect:
      artifactMatches(path, fileName, groupId, artifactId, version, classifier, extension) == result
    where:
      // @formatter:off
      path                                   | fileName               | groupId   | artifactId | version | classifier | extension     || result
      'foo/bar/baz/1.0/baz-1.0.jar'          | 'baz-1.0.jar'          | 'foo.bar' | 'baz'      | '1.0'   | null       | 'jar'         || true
      'foo/bar/baz/1.0/baz-1.0.jar.sha1'     | 'baz-1.0.jar.sha1'     | 'foo.bar' | 'baz'      | '1.0'   | null       | 'jar.sha1'    || true
      'foo/bar/baz/1.0/baz-1.0.jar.asc'      | 'baz-1.0.jar.asc'      | 'foo.bar' | 'baz'      | '1.0'   | null       | 'jar.asc'     || true
      'foo/bar/baz/1.0/baz-1.0.jar.info'     | 'baz-1.0.jar.info'     | 'foo.bar' | 'baz'      | '1.0'   | null       | 'jar.info'    || true
      'foo/bar/baz/1.0/baz-1.0.tar.gz'       | 'baz-1.0.tar.gz'       | 'foo.bar' | 'baz'      | '1.0'   | null       | 'tar.gz'      || true
      'foo/bar/baz/1.0/baz-1.0.tar.gz.sha1'  | 'baz-1.0.tar.gz.sha1'  | 'foo.bar' | 'baz'      | '1.0'   | null       | 'tar.gz.sha1' || true
      'foo/bar/baz/1.0/baz-1.0.tar.gz.asc'   | 'baz-1.0.tar.gz.asc'   | 'foo.bar' | 'baz'      | '1.0'   | null       | 'tar.gz.asc'  || true
      'foo/bar/baz/1.0/baz-1.0.tar.gz.info'  | 'baz-1.0.tar.gz.info'  | 'foo.bar' | 'baz'      | '1.0'   | null       | 'tar.gz.info' || true
      'foo/bar/baz/1.0/baz-1.0-qux.jar'      | 'baz-1.0-qux.jar'      | 'foo.bar' | 'baz'      | '1.0'   | 'qux'      | 'jar'         || true
      'foo/bar/baz/1.0/baz-1.0-qux.jar.sha1' | 'baz-1.0-qux.jar.sha1' | 'foo.bar' | 'baz'      | '1.0'   | 'qux'      | 'jar.sha1'    || true
      'foo/bar/baz/1.0/baz-1.0-qux.jar.asc'  | 'baz-1.0-qux.jar.asc'  | 'foo.bar' | 'baz'      | '1.0'   | 'qux'      | 'jar.asc'     || true
      'foo/bar/baz/1.0/baz-1.0-qux.jar.info' | 'baz-1.0-qux.jar.info' | 'foo.bar' | 'baz'      | '1.0'   | 'qux'      | 'jar.info'    || true

      // HACK: ATM artifact-path copes with legacy non-timestamp-build SNAPSHOT paths
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-SNAPSHOT.jar'          | 'baz-1.0-SNAPSHOT.jar'          | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT'   | null       | 'jar'         || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-SNAPSHOT.jar.sha1'     | 'baz-1.0-SNAPSHOT.jar.sha1'     | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT'   | null       | 'jar.sha1'    || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-SNAPSHOT.jar.asc'      | 'baz-1.0-SNAPSHOT.jar.asc'      | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT'   | null       | 'jar.asc'     || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-SNAPSHOT.jar.info'     | 'baz-1.0-SNAPSHOT.jar.info'     | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT'   | null       | 'jar.info'    || true
      // @formatter:on
  }

  private boolean snapshotArtifactMatches(final String path,
                                          final String fileName,
                                          final String groupId,
                                          final String artifactId,
                                          final String baseVersion,
                                          final String version,
                                          final String timestamp,
                                          final String build,
                                          final String classifier,
                                          final String extension)
  {
    MavenPath parsed = Maven2PathParser.parse(path)
    if (parsed == null || parsed.path != path || parsed.fileName != fileName) {
      return false
    }
    if (parsed instanceof SnapshotArtifactPath) {
      return parsed.groupId == groupId &&
          parsed.artifactId == artifactId &&
          parsed.baseVersion == baseVersion &&
          parsed.version == version &&
          parsed.timestamp == timestamp &&
          parsed.build == build &&
          parsed.classifier == classifier &&
          parsed.extension == extension
    }
    return false
  }

  @Test
  void 'parse SNAPSHOT artifact'() {
    expect:
      snapshotArtifactMatches(
          path, fileName, groupId, artifactId, baseVersion, version, timestamp, build, classifier, extension) == result
    where:
      // @formatter:off
      path                                                              | fileName                                 | groupId   | artifactId | baseVersion    | version                 | timestamp         | build | classifier | extension     || result
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar'          | 'baz-1.0-20191029.053716-1.jar'          | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'jar'         || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar.sha1'     | 'baz-1.0-20191029.053716-1.jar.sha1'     | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'jar.sha1'    || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar.asc'      | 'baz-1.0-20191029.053716-1.jar.asc'      | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'jar.asc'     || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar.info'     | 'baz-1.0-20191029.053716-1.jar.info'     | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'jar.info'    || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz'       | 'baz-1.0-20191029.053716-1.tar.gz'       | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'tar.gz'      || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz.sha1'  | 'baz-1.0-20191029.053716-1.tar.gz.sha1'  | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'tar.gz.sha1' || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz.asc'   | 'baz-1.0-20191029.053716-1.tar.gz.asc'   | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'tar.gz.asc'  || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz.info'  | 'baz-1.0-20191029.053716-1.tar.gz.info'  | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | null       | 'tar.gz.info' || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar'      | 'baz-1.0-20191029.053716-1-qux.jar'      | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | 'qux'      | 'jar'         || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar.sha1' | 'baz-1.0-20191029.053716-1-qux.jar.sha1' | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | 'qux'      | 'jar.sha1'    || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar.asc'  | 'baz-1.0-20191029.053716-1-qux.jar.asc'  | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | 'qux'      | 'jar.asc'     || true
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar.info' | 'baz-1.0-20191029.053716-1-qux.jar.info' | 'foo.bar' | 'baz'      | '1.0-SNAPSHOT' | '1.0-20191029.053716-1' | '20191029.053716' | '1'   | 'qux'      | 'jar.info'    || true
      // @formatter:on
  }
}
