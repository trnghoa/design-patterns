/*
 * @(#)ProxyDemo.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.proxy;
import java.util.List;
/**
 * Runs the Proxy example. The same list of downloads runs twice: once straight against the
 * real storage, and once through the caching proxy.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ProxyDemo {
  public static void main(String[] args) {
    List<String> requests = List.of(
        "ORIGINAL/12-harbour-street/living-room.jpg",
        "ORIGINAL/12-harbour-street/kitchen.jpg",
        "ORIGINAL/12-harbour-street/living-room.jpg",
        "ORIGINAL/12-harbour-street/living-room.jpg");

    System.out.println("Without proxy:");
    RemoteImageStorage remote = new RemoteImageStorage();
    run(remote, requests);
    System.out.println("  remote downloads: " + remote.getDownloadCount());

    System.out.println("With caching proxy:");
    RemoteImageStorage remoteBehindProxy = new RemoteImageStorage();
    run(new CachingImageStorageProxy(remoteBehindProxy), requests);
    System.out.println("  remote downloads: " + remoteBehindProxy.getDownloadCount());
  }

  /** The caller only knows {@link ImageStorage}, so it works the same with or without proxy. */
  private static void run(ImageStorage storage, List<String> requests) {
    for (String fileKey : requests) {
      storage.download(fileKey);
    }
  }
}
