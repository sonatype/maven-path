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
  public final String suffix;

  /**
   * Signature algorithm name.
   */
  public final String algorithm;

  public final String contentType;

  SignatureType(final String suffix, final String algorithm, final String contentType) {
    this.suffix = requireNonNull(suffix);
    this.algorithm = requireNonNull(algorithm);
    this.contentType = requireNonNull(contentType);
  }

  public boolean pathMatches(final String path) {
    return path.endsWith("." + suffix);
  }

  public String pathOf(final String path) {
    return path + "." + suffix;
  }

  //
  // Helpers
  //

  @Nullable
  public static SignatureType ofPath(final String path) {
    for (SignatureType value : SignatureType.values()) {
      if (value.pathMatches(path)) {
        return value;
      }
    }
    return null;
  }
}
