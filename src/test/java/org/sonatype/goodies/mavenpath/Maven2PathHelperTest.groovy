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

/**
 * {@link Maven2PathHelper} tests.
 */
class Maven2PathHelperTest
{
  @Test
  void 'gav path'() {
    Maven2PathHelper.artifactPath('com.sonatype', 'foo', '1.0', null, 'jar').with {
      println it
      assert it == 'com/sonatype/foo/1.0/foo-1.0.jar'
    }

    Maven2PathHelper.artifactPath('com.sonatype', 'foo', '1.0', 'bar', 'jar').with {
      println it
      assert it == 'com/sonatype/foo/1.0/foo-1.0-bar.jar'
    }
  }

  @Test
  void 'gav path-prefix'() {
    Maven2PathHelper.artifactPathPrefix('com.sonatype', 'foo', '1.0').with {
      println it
      assert it == 'com/sonatype/foo/1.0/foo-1.0'
    }
  }

  @Test
  void 'gav as pom-path'() {
    Maven2PathHelper.pomPath('com.sonatype', 'foo', '1.0').with {
      println it
      assert it == 'com/sonatype/foo/1.0/foo-1.0.pom'
    }
  }

  @Test
  void 'artifact filename'() {
    Maven2PathHelper.artifactFilename('foo', '1.0', null, 'jar').with {
      println it
      assert it == 'foo-1.0.jar'
    }
  }

  @Test
  void 'artifact filename with classifier'() {
    Maven2PathHelper.artifactFilename('foo', '1.0', 'sources', 'jar').with {
      println it
      assert it == 'foo-1.0-sources.jar'
    }
  }
}
