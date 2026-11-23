/*
 * @(#)VolumeDiscountDecorator.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.decorator;
import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * A <em>concrete decorator</em>: takes 5 percent off when the wrapped order's total is more
 * than 20 dollars. An order of exactly 20 dollars gets no discount.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class VolumeDiscountDecorator extends OrderDecorator {
  static final BigDecimal THRESHOLD = new BigDecimal("20.00");
  static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.05");
  public VolumeDiscountDecorator(Order wrapped) {
    super(wrapped);
  }
  @Override
  public BigDecimal getTotal() {
    return wrapped.getTotal().subtract(getDiscount());
  }
  @Override
  public String describe() {
    BigDecimal discount = getDiscount();
    if (discount.signum() == 0) {
      return wrapped.describe() + " (no discount, total not over $" + THRESHOLD + ")";
    }
    return "%s - 5%% discount $%s = $%s".formatted(wrapped.describe(), discount, getTotal());
  }
  private BigDecimal getDiscount() {
    BigDecimal base = wrapped.getTotal();
    if (base.compareTo(THRESHOLD) <= 0) {
      return BigDecimal.ZERO.setScale(2);
    }
    return base.multiply(DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP);
  }
}
