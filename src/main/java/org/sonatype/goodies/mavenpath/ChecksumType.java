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

import javax.annotation.Nullable;

//import com.google.common.hash.HashCode;
//import com.google.common.hash.HashFunction;
//import com.google.common.hash.Hashing;
//import com.google.common.hash.HashingInputStream;
//import com.google.common.io.ByteStreams;

import static java.util.Objects.requireNonNull;

// see: https://github.com/sonatype/nexus2-internal/blob/master/private/plugins/clm/nexus-staging-plugin/src/main/java/com/sonatype/nexus/staging/internal/rules/ChecksumStagingRuleEvaluator.java

/**
 * Supported checksum types.
 *
 * @since ???
 */
public enum ChecksumType
{
  SHA_1("sha1", "SHA-1"/*, Hashing.sha1()*/),
  SHA_256("sha256", "SHA-256"/*, Hashing.sha256()*/),
  SHA_512("sha512", "SHA-512"/*, Hashing.sha512()*/),
  MD5("md5", "MD5"/*, Hashing.md5()*/);

  public static String CHECKSUM_CONTENT_TYPE = "text/plain";

  /**
   * File-extension suffix.
   */
  public final String suffix;

  /**
   * Hash algorithm name.
   */
  public final String algorithm;

  ///**
  // * Hash function.
  // */
  //public final HashFunction hashFunction;

  ChecksumType(final String suffix, final String algorithm/*, final HashFunction hashFunction*/) {
    this.suffix = requireNonNull(suffix);
    this.algorithm = requireNonNull(algorithm);
    //this.hashFunction = requireNonNull(hashFunction);
  }

  public boolean pathMatches(final String path) {
    return path.endsWith("." + suffix);
  }

  public String pathOf(final String path) {
    return path + "." + suffix;
  }

  //public HashCode hash(final InputStream content) throws IOException {
  //  try (HashingInputStream hashing = new HashingInputStream(hashFunction, content)) {
  //    ByteStreams.exhaust(hashing);
  //    return hashing.hash();
  //  }
  //}

  //
  // Helpers
  //

  @Nullable
  public static ChecksumType ofPath(final String path) {
    for (ChecksumType value : ChecksumType.values()) {
      if (value.pathMatches(path)) {
        return value;
      }
    }
    return null;
  }
}
