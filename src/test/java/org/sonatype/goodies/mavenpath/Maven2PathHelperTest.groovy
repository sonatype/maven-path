/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath

import org.junit.Test

/**
 * {@link Maven2PathHelper} tests.
 */
class Maven2PathHelperTest
{
  @Test
  void 'gav as path'() {
    Maven2PathHelper.pathPrefix('com.sonatype', 'foo', '1.0').with {
      println it
      assert it == 'com/sonatype/foo/1.0/foo-1.0'
    }
  }

  @Test
  void 'gav as pom path'() {
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
