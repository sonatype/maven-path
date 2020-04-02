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

import java.util.regex.Pattern

import org.junit.Test

/**
 * {@link Maven2PathParser} trials.
 */
class Maven2PathParserTrial
{
  Pattern mavenMetadata = Pattern.compile(
      '^(?<prefix>.+)/maven-metadata\\.xml(\\.(?<subtype>.+))?$'
  )

  Pattern artifactPattern = Pattern.compile(
      '^(?<group>.+)/(?<artifact>[^/]+)/(?<version>[^/]+)/(?<filename>\\k<artifact>-\\k<version>(-(?<classifier>[^.]+))?\\.(?<type>.+))$'
  )

  Pattern snapshotPattern = Pattern.compile(
      '^(?<group>.+)/(?<artifact>[^/]+)/(?<bversion>(?<vprefix>[^/]+)-SNAPSHOT)/(?<filename>\\k<artifact>-(?<version>\\k<vprefix>-(?<vsuffix>(?<vtimestamp>\\d+\\.\\d+)-(?<vbuild>\\d+)))(-(?<classifier>[^.]+))?\\.(?<type>.+))$',
      Pattern.CASE_INSENSITIVE
  )

  Pattern genericPattern = Pattern.compile(
      '^(?<group>.+)/(?<artifact>[^/]+)/(?<version>[^/]+)/(?<filename>.+)$'
  )

  def paths = [
      'foo/bar/baz/maven-metadata.xml',
      'foo/bar/baz/maven-metadata.xml.sha1',
      'foo/bar/baz/maven-metadata.xml.asc',
      'foo/bar/baz/maven-metadata.xml.info',

      'foo/bar/baz/1.0/baz-1.0.jar',
      'foo/bar/baz/1.0/baz-1.0.jar.sha1',
      'foo/bar/baz/1.0/baz-1.0.jar.asc',
      'foo/bar/baz/1.0/baz-1.0.jar.info',

      'foo/bar/baz/1.0/baz-1.0.tar.gz',
      'foo/bar/baz/1.0/baz-1.0.tar.gz.sha1',
      'foo/bar/baz/1.0/baz-1.0.tar.gz.asc',
      'foo/bar/baz/1.0/baz-1.0.tar.gz.info',

      'foo/bar/baz/1.0/baz-1.0-qux.jar',
      'foo/bar/baz/1.0/baz-1.0-qux.jar.sha1',
      'foo/bar/baz/1.0/baz-1.0-qux.jar.asc',
      'foo/bar/baz/1.0/baz-1.0-qux.jar.info',

      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar.sha1',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar.asc',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar.info',

      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz.sha1',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz.asc',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.tar.gz.info',

      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar.sha1',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar.asc',
      'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar.info',

      'foo/bar/baz/1.0-snapshot/baz-1.0-20191029.053716-1.jar',
      'foo/bar/baz/1.0-snapshot/baz-1.0-20191029.053716-1.jar.sha1',
      'foo/bar/baz/1.0-snapshot/baz-1.0-20191029.053716-1.jar.asc',
      'foo/bar/baz/1.0-snapshot/baz-1.0-20191029.053716-1.jar.info',
  ]

  @Test
  void 'maven-metadata.xml'() {
    paths.each { path ->
      def m = mavenMetadata.matcher(path)
      if (m.matches()) {
        println "MATCH: $path"
        ['prefix', 'subtype'].each { name ->
          println "  $name: ${m.group(name)}"
        }
      }
      else {
        println "MISS: $path"
      }
    }
  }

  @Test
  void 'artifact'() {
    paths.each { path ->
      def m = artifactPattern.matcher(path)
      if (m.matches()) {
        println "MATCH: $path"
        ['group', 'artifact', 'version', 'filename', 'classifier', 'type'].each { name ->
          println "  $name: ${m.group(name)}"
        }
      }
      else {
        println "MISS: $path"
      }
    }
  }

  @Test
  void 'snapshot'() {
    paths.each { path ->
      def m = snapshotPattern.matcher(path)
      if (m.matches()) {
        println "MATCH: $path"
        ['group', 'artifact', 'bversion', 'vprefix', 'filename', 'version', 'vsuffix', 'vtimestamp', 'vbuild', 'classifier', 'type'].each { name ->
          println "  $name: ${m.group(name)}"
        }
      }
      else {
        println "MISS: $path"
      }
    }
  }
}
