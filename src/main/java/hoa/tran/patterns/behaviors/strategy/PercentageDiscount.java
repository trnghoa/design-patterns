/*
 * @(#)PercentageDiscount.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;

/**
 * Discount: takes a percentage off the whole order, e.g. 10% off.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class PercentageDiscount implements PricingStrategy {

  private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

  private final BigDecimal percent;

  public PercentageDiscount(BigDecimal percent) {
    if (percent.signum() < 0 || percent.compareTo(HUNDRED) > 0) {
      throw new IllegalArgumentException("percent must be between 0 and 100, was " + percent);
    }
    this.percent = percent;
  }

  @Override
  public BigDecimal discountFor(Product product, int quantity) {
    BigDecimal subtotal = Money.subtotal(product, quantity);
    return Money.cents(subtotal.multiply(percent).divide(HUNDRED));
  }

  @Override
  public String label() {
    return percent.stripTrailingZeros().toPlainString() + "% discount";
  }
}
