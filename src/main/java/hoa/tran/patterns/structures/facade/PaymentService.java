/*
 * @(#)PaymentService.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
import java.math.BigDecimal;
/**
 * Subsystem: charges the customer's card.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class PaymentService {
  static final BigDecimal CARD_LIMIT = new BigDecimal("1000.00");
  /** Returns {@code false} when the amount is over the card limit. */
  public boolean charge(String customerId, BigDecimal amount) {
    if (amount.compareTo(CARD_LIMIT) > 0) {
      System.out.println("  [payment]   declined $" + amount + " for " + customerId);
      return false;
    }
    System.out.println("  [payment]   charged $" + amount + " to " + customerId);
    return true;
  }
}
