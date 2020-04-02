/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

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
    this.suffix = checkNotNull(suffix);
    this.algorithm = checkNotNull(algorithm);
    this.contentType = checkNotNull(contentType);
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
