/*
 * @(#)NotificationService.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
/**
 * Subsystem: sends messages to the customer.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class NotificationService {
  public void send(String customerId, String message) {
    System.out.println("  [email]     to " + customerId + ": " + message);
  }
}
