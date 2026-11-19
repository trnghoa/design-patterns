/*
 * @(#)ImageStorage.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.proxy;
/**
 * Where images are downloaded from. Both the real storage and the proxy implement this, so
 * callers cannot tell them apart.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface ImageStorage {
  /** Returns the content of the image stored under {@code fileKey}. */
  String download(String fileKey);
}
