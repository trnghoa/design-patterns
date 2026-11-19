/*
 * @(#)CachingImageStorageProxy.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.proxy;
import java.util.HashMap;
import java.util.Map;
/**
 * The proxy. It stands in front of the real {@link ImageStorage} and has the same interface.
 * The first request for a file goes to the real storage; later requests for the same file are
 * answered from memory.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class CachingImageStorageProxy implements ImageStorage {
  private final ImageStorage realStorage;
  private final Map<String, String> cache = new HashMap<>();
  public CachingImageStorageProxy(ImageStorage realStorage) {
    this.realStorage = realStorage;
  }
  @Override
  public String download(String fileKey) {
    String cached = cache.get(fileKey);
    if (cached != null) {
      System.out.println("  [proxy]  cache hit for " + fileKey);
      return cached;
    }
    String content = realStorage.download(fileKey);
    cache.put(fileKey, content);
    return content;
  }
}
