/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath

import org.junit.Test

/**
 * {@link Maven2PathFactory} tests.
 */
class Maven2PathFactoryTest
{
  @Test
  void 'create maven-metadata'() {
    Maven2PathFactory.createMavenMetadata('foo/bar/baz', null).with {
      println it
      assert it instanceof MavenMetadataPath
      assert it.path == "foo/bar/baz/${Maven2PathFactory.MAVEN_METADATA_FILENAME}"
      assert it.fileName == Maven2PathFactory.MAVEN_METADATA_FILENAME
      assert it.subordinateType == null
    }
  }

  @Test
  void 'create maven-metadata subordinate'() {
    Maven2PathFactory.createMavenMetadata('foo/bar/baz', 'sha1').with {
      assert it instanceof MavenMetadataPath
      assert it.path == "foo/bar/baz/${Maven2PathFactory.MAVEN_METADATA_FILENAME}.sha1"
      assert it.fileName == "${Maven2PathFactory.MAVEN_METADATA_FILENAME}.sha1"
      assert it.subordinateType == 'sha1'
    }
  }

  @Test
  void 'create artifact'() {
    Maven2PathFactory.createArtifact('foo.bar', 'baz', '1.0', null, 'jar').with {
      println it
      assert it instanceof ArtifactPath
      assert it.path == 'foo/bar/baz/1.0/baz-1.0.jar'
      assert it.fileName == 'baz-1.0.jar'
      assert it.groupId == 'foo.bar'
      assert it.artifactId == 'baz'
      assert it.version == '1.0'
      assert it.classifier == null
      assert it.type == 'jar'
    }
  }

  @Test
  void 'create artifact with classifier'() {
    Maven2PathFactory.createArtifact('foo.bar', 'baz', '1.0', 'qux', 'jar').with {
      println it
      assert it instanceof ArtifactPath
      assert it.path == 'foo/bar/baz/1.0/baz-1.0-qux.jar'
      assert it.fileName == 'baz-1.0-qux.jar'
      assert it.groupId == 'foo.bar'
      assert it.artifactId == 'baz'
      assert it.version == '1.0'
      assert it.classifier == 'qux'
      assert it.type == 'jar'
    }
  }

  @Test
  void 'create snapshot artifact'() {
    Maven2PathFactory.createSnapshotArtifact('foo.bar', 'baz', '1.0-SNAPSHOT', '1.0-20191029.053716-1', '20191029.053716', '1', null, 'jar').with {
      println it
      assert it instanceof SnapshotArtifactPath
      assert it.path == 'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1.jar'
      assert it.fileName == 'baz-1.0-20191029.053716-1.jar'
      assert it.groupId == 'foo.bar'
      assert it.artifactId == 'baz'
      assert it.baseVersion == '1.0-SNAPSHOT'
      assert it.version == '1.0-20191029.053716-1'
      assert it.timestamp == '20191029.053716'
      assert it.build == '1'
      assert it.classifier == null
      assert it.type == 'jar'
    }
  }

  @Test
  void 'create snapshot artifact with classifier'() {
    Maven2PathFactory.createSnapshotArtifact('foo.bar', 'baz', '1.0-SNAPSHOT', '1.0-20191029.053716-1', '20191029.053716', '1', 'qux', 'jar').with {
      println it
      assert it instanceof SnapshotArtifactPath
      assert it.path == 'foo/bar/baz/1.0-SNAPSHOT/baz-1.0-20191029.053716-1-qux.jar'
      assert it.fileName == 'baz-1.0-20191029.053716-1-qux.jar'
      assert it.groupId == 'foo.bar'
      assert it.artifactId == 'baz'
      assert it.baseVersion == '1.0-SNAPSHOT'
      assert it.version == '1.0-20191029.053716-1'
      assert it.timestamp == '20191029.053716'
      assert it.build == '1'
      assert it.classifier == 'qux'
      assert it.type == 'jar'
    }
  }
}
