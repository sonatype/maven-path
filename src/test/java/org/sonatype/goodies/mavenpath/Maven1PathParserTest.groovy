/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath

import org.junit.Test
import spock.lang.Specification

/**
 * {@link Maven1PathParser} tests.
 */
class Maven1PathParserTest
    extends Specification
{
  private boolean artifactMatches(final String path,
                                  final String fileName,
                                  final String groupId,
                                  final String artifactId,
                                  final String version,
                                  final String classifier,
                                  final String type,
                                  final boolean snapshot)
  {
    MavenPath parsed = Maven1PathParser.parse(path)
    if (parsed == null || parsed.path != path || parsed.fileName != fileName) {
      return false
    }
    if (parsed instanceof ArtifactPath) {
      return parsed.groupId == groupId &&
          parsed.artifactId == artifactId &&
          parsed.version == version &&
          parsed.classifier == classifier &&
          parsed.type == type &&
          (parsed instanceof SnapshotArtifactPath) == snapshot
    }
    return false
  }

  @Test
  void 'parse artifact'() {
    expect:
      artifactMatches(path, fileName, groupId, artifactId, version, classifier, type, snapshot) == result
    where:
      // @formatter:off
      path                                                    | fileName                                    | groupId         | artifactId      | version           | classifier | type       | snapshot || result
      'activecluster/poms/activecluster-1.0-SNAPSHOT.pom'     | 'activecluster-1.0-SNAPSHOT.pom'            | 'activecluster' | 'activecluster' | '1.0-SNAPSHOT'    | null       | 'pom'      | true     || true
      // SEE: https://github.com/apache/maven-indexer/blob/master/indexer-core/src/test/java/org/apache/maven/index/artifact/M1GavCalculatorTest.java
      'org.jruby/javadocs/jruby-1.0RC1-SNAPSHOT-javadoc.jar'  | 'jruby-1.0RC1-SNAPSHOT-javadoc.jar'         | 'org.jruby'     | 'jruby'         | '1.0RC1-SNAPSHOT' | 'javadoc'  | 'jar'      | true     || true
      'org.jruby/jars/jruby-1.0RC1-SNAPSHOT.jar'              | 'jruby-1.0RC1-SNAPSHOT.jar'                 | 'org.jruby'     | 'jruby'         | '1.0RC1-SNAPSHOT' | null       | 'jar'      | true     || true
      'org.jruby/jars/jruby-1.0RC1-SNAPSHOT.jar.md5'          | 'jruby-1.0RC1-SNAPSHOT.jar.md5'             | 'org.jruby'     | 'jruby'         | '1.0RC1-SNAPSHOT' | null       | 'jar.md5'  | true     || true
      'org.jruby/javadocs/jruby-1.0-javadoc.jar'              | 'jruby-1.0-javadoc.jar'                     | 'org.jruby'     | 'jruby'         | '1.0'             | 'javadoc'  | 'jar'      | false    || true
      'org.jruby/javadocs/jruby-1.0-javadoc.jar.sha1'         | 'jruby-1.0-javadoc.jar.sha1'                | 'org.jruby'     | 'jruby'         | '1.0'             | 'javadoc'  | 'jar.sha1' | false    || true
      'org.jruby/jars/jruby-1.0.jar'                          | 'jruby-1.0.jar'                             | 'org.jruby'     | 'jruby'         | '1.0'             | null       | 'jar'      | false    || true
      'maven/jars/dom4j-1.7-20060614.jar'                     | 'dom4j-1.7-20060614.jar'                    | 'maven'         | 'dom4j'         | '1.7-20060614'    | null       | 'jar'      | false    || true
      'maven/java-sources/velocity-1.5-SNAPSHOT-sources.jar'  | 'velocity-1.5-SNAPSHOT-sources.jar'         | 'maven'         | 'velocity'      | '1.5-SNAPSHOT'    | 'sources'  | 'jar'      | true     || true
      'castor/jars/castor-0.9.9-xml.jar'                      | 'castor-0.9.9-xml.jar'                      | 'castor'        | 'castor'        | '0.9.9-xml'       | null       | 'jar'      | false    || true
      'org.slf4j/poms/slf4j-log4j12-1.4.3.pom'                | 'slf4j-log4j12-1.4.3.pom'                   | 'org.slf4j'     | 'slf4j-log4j12' | '1.4.3'           | null       | 'pom'      | false    || true
      'xpp3/poms/xpp3_min-1.1.3.4.O.pom'                      | 'xpp3_min-1.1.3.4.O.pom'                    | 'xpp3'          | 'xpp3_min'      | '1.1.3.4.O'/*oh*/ | null       | 'pom'      | false    || true
      'castor/ejbs/castor-ejb-1.0.7.jar'                      | 'castor-ejb-1.0.7.jar'                      | 'castor'        | 'castor-ejb'    | '1.0.7'           | null       | 'jar'      | false    || true
      'castor/ejbs/castor-ejb-1.0.7-client.jar'               | 'castor-ejb-1.0.7-client.jar'               | 'castor'        | 'castor-ejb'    | '1.0.7'           | 'client'   | 'jar'      | false    || true
      'castor/ejbs/castor-ejb-1.0.7-SNAPSHOT-client.jar'      | 'castor-ejb-1.0.7-SNAPSHOT-client.jar'      | 'castor'        | 'castor-ejb'    | '1.0.7-SNAPSHOT'  | 'client'   | 'jar'      | true     || true
      'castor/ejbs/castor-ejb-1.0.7-SNAPSHOT-client.jar.sha1' | 'castor-ejb-1.0.7-SNAPSHOT-client.jar.sha1' | 'castor'        | 'castor-ejb'    | '1.0.7-SNAPSHOT'  | 'client'   | 'jar.sha1' | true     || true
      // @formatter:on
  }

  @Test
  void 'parse non-artifact'() {
    expect:
      !artifactMatches(path, null, null, null, null, null, null, false)
    where:
      path                                              | _
      'foo/bar/baz/1.0/baz-1.0.jar'                     | _
      'foo/bar/baz/1.0/baz-1.0.jar.sha1'                | _
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-SNAPSHOT.jar'   | _
      // SEE: https://github.com/apache/maven-indexer/blob/master/indexer-core/src/test/java/org/apache/maven/index/artifact/M1GavCalculatorTest.java
      'some/stupid/path'                                | _
      'some/stupid/path/more/in/it'                     | _
      'something/that/looks'                            | _
      'something/that/like-an-artifact.blah'            | _
      'something/that/like-an-artifact.pom'             | _
      'something/that/maven-metadata.xml'               | _
      'something/that/like-SNAPSHOT/maven-metadata.xml' | _
  }
}
