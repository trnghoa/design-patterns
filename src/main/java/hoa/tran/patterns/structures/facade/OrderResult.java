/*
 * @(#)OrderResult.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
/**
 * What the facade returns to the caller: whether the order went through, and why not if it
 * didn't.
 *
 * @author hoa.tn
 * @since 1.0
 */
public record OrderResult(boolean success, String trackingNumber, String message) {
  static OrderResult placed(String trackingNumber) {
    return new OrderResult(true, trackingNumber, "Order placed");
  }
  static OrderResult failed(String message) {
    return new OrderResult(false, null, message);
  }
}
