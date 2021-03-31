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

/**
 * Supported signature types.
 *
 * @since ???
 */
public enum SignatureType
{
  PGP("asc", "PGP", "application/pgp-signature");

  /**
   * File-extension suffix.
   */
  public final String extension;

  /**
   * Signature algorithm name.
   */
  public final String algorithm;

  /**
   * Content type.
   */
  public final String contentType;

  SignatureType(final String extension, final String algorithm, final String contentType) {
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
  public static SignatureType forAlgorithm(final String algorithm) {
    for (SignatureType type : values()) {
      if (type.algorithm.equals(algorithm)) {
        return type;
      }
    }
    return null;
  }

  @Nullable
  public static SignatureType ofPath(final String path) {
    for (SignatureType value : values()) {
      if (value.pathMatches(path)) {
        return value;
      }
    }
    return null;
  }
}
