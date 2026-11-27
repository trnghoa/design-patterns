/*
 * @(#)RegularPrice.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;

/**
 * No offer: the customer pays the full price. {@link Checkout} uses this when no other
 * strategy is chosen, so it never has to check for {@code null}.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class RegularPrice implements PricingStrategy {

  @Override
  public BigDecimal discountFor(Product product, int quantity) {
    return Money.ZERO;
  }

  @Override
  public String label() {
    return "regular price";
  }
}
