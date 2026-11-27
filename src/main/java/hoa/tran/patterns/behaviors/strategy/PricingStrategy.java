/*
 * @(#)PricingStrategy.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;

/**
 * The strategy. Each implementation is one way of working out how much to take off an order.
 * {@link Checkout} only knows this interface, so a new kind of offer is a new class and
 * {@code Checkout} never changes.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface PricingStrategy {

  /**
   * Returns the amount to take off, in EUR. It is never negative and never more than the
   * order's subtotal ({@code unitPrice x quantity}).
   */
  BigDecimal discountFor(Product product, int quantity);

  /** Short text for the receipt, e.g. "10% discount". */
  String label();
}
