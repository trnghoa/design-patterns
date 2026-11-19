/*
 * @(#)ProxyTest.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Checks that the proxy returns the same content as the real storage, but only calls the
 * real storage once per file.
 *
 * @author hoa.tn
 * @since 1.0
 */
class ProxyTest {

  private static final String LIVING_ROOM = "ORIGINAL/12-harbour-street/living-room.jpg";
  private static final String KITCHEN = "ORIGINAL/12-harbour-street/kitchen.jpg";

  @Test
  void proxyReturnsSameContentAsRealStorage() {
    String direct = new RemoteImageStorage().download(LIVING_ROOM);
    String viaProxy = new CachingImageStorageProxy(new RemoteImageStorage()).download(LIVING_ROOM);
    assertEquals(direct, viaProxy);
  }

  @Test
  void repeatedDownloadHitsRealStorageOnce() {
    RemoteImageStorage remote = new RemoteImageStorage();
    ImageStorage proxy = new CachingImageStorageProxy(remote);

    proxy.download(LIVING_ROOM);
    proxy.download(LIVING_ROOM);
    proxy.download(LIVING_ROOM);

    assertEquals(1, remote.getDownloadCount());
  }

  @Test
  void differentFilesAreEachDownloaded() {
    RemoteImageStorage remote = new RemoteImageStorage();
    ImageStorage proxy = new CachingImageStorageProxy(remote);

    proxy.download(LIVING_ROOM);
    proxy.download(KITCHEN);
    proxy.download(LIVING_ROOM);

    assertEquals(2, remote.getDownloadCount());
  }
}
