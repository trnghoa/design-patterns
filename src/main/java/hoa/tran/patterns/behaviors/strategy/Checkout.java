/*
 * @(#)Checkout.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;

/**
 * The context. It prices an order but hands the "how much off?" question to whichever
 * {@link PricingStrategy} it holds. The strategy can be swapped at runtime with
 * {@link #setStrategy(PricingStrategy)}, and this class has no {@code if} or {@code switch}
 * on the kind of offer.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class Checkout {

  private PricingStrategy strategy = new RegularPrice();

  public void setStrategy(PricingStrategy strategy) {
    this.strategy = strategy == null ? new RegularPrice() : strategy;
  }

  public BigDecimal total(Product product, int quantity) {
    return Money.subtotal(product, quantity).subtract(discount(product, quantity));
  }

  public String receipt(Product product, int quantity) {
    return "%d x %s @ %s EUR = %s EUR, %s -%s EUR, total %s EUR".formatted(
        quantity, product.name(), Money.cents(product.unitPrice()),
        Money.subtotal(product, quantity), strategy.label(),
        discount(product, quantity), total(product, quantity));
  }

  /** Asks the strategy, then makes sure the result is between zero and the subtotal. */
  private BigDecimal discount(Product product, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("quantity must be at least 1, was " + quantity);
    }
    BigDecimal subtotal = Money.subtotal(product, quantity);
    BigDecimal discount = Money.cents(strategy.discountFor(product, quantity));
    return discount.max(Money.ZERO).min(subtotal);
  }
}
