/*
 * @(#)Voucher.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;

/**
 * Voucher: a code worth a fixed amount, e.g. {@code WELCOME20} takes 20 EUR off. It only
 * applies when the order reaches the minimum spend, and it never takes off more than the
 * order costs.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class Voucher implements PricingStrategy {

  private final String code;
  private final BigDecimal amount;
  private final BigDecimal minimumSpend;

  public Voucher(String code, BigDecimal amount, BigDecimal minimumSpend) {
    if (amount.signum() < 0 || minimumSpend.signum() < 0) {
      throw new IllegalArgumentException("amount and minimumSpend must not be negative");
    }
    this.code = code;
    this.amount = Money.cents(amount);
    this.minimumSpend = Money.cents(minimumSpend);
  }

  @Override
  public BigDecimal discountFor(Product product, int quantity) {
    BigDecimal subtotal = Money.subtotal(product, quantity);
    if (subtotal.compareTo(minimumSpend) < 0) {
      return Money.ZERO;
    }
    return amount.min(subtotal);
  }

  @Override
  public String label() {
    return "voucher %s (%s EUR off, min. spend %s EUR)".formatted(code, amount, minimumSpend);
  }
}
