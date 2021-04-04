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

import static java.util.Objects.requireNonNull;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.ALGORITHM_MD5;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.ALGORITHM_SHA_1;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.ALGORITHM_SHA_256;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.ALGORITHM_SHA_512;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.EXTENSION_MD5;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.EXTENSION_SHA_1;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.EXTENSION_SHA_256;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.EXTENSION_SHA_512;
import static org.sonatype.goodies.mavenpath.ChecksumType.Constants.TEXT_PLAIN;

// see: https://github.com/sonatype/nexus2-internal/blob/master/private/plugins/clm/nexus-staging-plugin/src/main/java/com/sonatype/nexus/staging/internal/rules/ChecksumStagingRuleEvaluator.java

/**
 * Supported checksum types.
 *
 * @since ???
 */
public enum ChecksumType
{
  SHA_1(EXTENSION_SHA_1, ALGORITHM_SHA_1, TEXT_PLAIN),
  SHA_256(EXTENSION_SHA_256, ALGORITHM_SHA_256, TEXT_PLAIN),
  SHA_512(EXTENSION_SHA_512, ALGORITHM_SHA_512, TEXT_PLAIN),
  MD5(EXTENSION_MD5, ALGORITHM_MD5, TEXT_PLAIN);

  /**
   * File-extension suffix.
   */
  public final String extension;

  /**
   * Hash algorithm name.
   */
  public final String algorithm;

  /**
   * Content type.
   */
  public final String contentType;

  ChecksumType(final String extension, final String algorithm, final String contentType) {
    this.extension = requireNonNull(extension);
    this.algorithm = requireNonNull(algorithm);
    this.contentType = requireNonNull(contentType);
  }

  public boolean pathMatches(final String path) {
    requireNonNull(path);
    return path.endsWith("." + extension);
  }

  public String pathOf(final String path) {
    requireNonNull(path);
    return path + "." + extension;
  }

  //
  // Helpers
  //

  @Nullable
  public static ChecksumType forAlgorithm(final String algorithm) {
    for (ChecksumType type : values()) {
      if (type.algorithm.equals(algorithm)) {
        return type;
      }
    }
    return null;
  }

  @Nullable
  public static ChecksumType ofPath(final String path) {
    for (ChecksumType value : values()) {
      if (value.pathMatches(path)) {
        return value;
      }
    }
    return null;
  }

  public static class Constants
  {
    public static final String TEXT_PLAIN = "text/plain";

    public static final String EXTENSION_SHA_1 = "sha1";

    public static final String EXTENSION_SHA_256 = "sha256";

    public static final String EXTENSION_SHA_512 = "sha512";

    public static final String EXTENSION_MD5 = "md5";

    public static final String ALGORITHM_SHA_1 = "SHA-1";

    public static final String ALGORITHM_SHA_256 = "SHA-256";

    public static final String ALGORITHM_SHA_512 = "SHA-512";

    public static final String ALGORITHM_MD5 = "MD5";
  }
}
