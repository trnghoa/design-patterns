/*
 * @(#)RemoteImageStorage.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.proxy;
/**
 * The real storage, for example an S3 bucket. Every download is slow, so it should not be
 * repeated for the same file.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class RemoteImageStorage implements ImageStorage {
  static final long DOWNLOAD_DELAY_MILLIS = 200;
  private int downloadCount;
  @Override
  public String download(String fileKey) {
    downloadCount++;
    System.out.println("  [remote] downloading " + fileKey + " ...");
    simulateNetworkDelay();
    return "<image bytes of " + fileKey + ">";
  }
  /** How many times this storage was actually called. Used to show the proxy's effect. */
  public int getDownloadCount() {
    return downloadCount;
  }
  private void simulateNetworkDelay() {
    try {
      Thread.sleep(DOWNLOAD_DELAY_MILLIS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
