/*
 * @(#)ShippingService.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
/**
 * Subsystem: books a delivery and returns its tracking number.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ShippingService {
  private int nextTrackingNumber = 1001;
  public String ship(String productId, int quantity, String address) {
    String trackingNumber = "TRK-" + nextTrackingNumber++;
    System.out.println("  [shipping]  " + quantity + " x " + productId + " to " + address
        + ", tracking " + trackingNumber);
    return trackingNumber;
  }
}
