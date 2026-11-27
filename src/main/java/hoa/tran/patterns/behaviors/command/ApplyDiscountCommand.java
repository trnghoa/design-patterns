/*
 * @(#)ApplyDiscountCommand.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * Discount: takes a percentage off what the order costs at the moment it is applied. If a
 * voucher was applied first, the percentage is taken from the lower total.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ApplyDiscountCommand extends ApplyAdjustmentCommand {

  private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

  private final BigDecimal percent;

  public ApplyDiscountCommand(Order order, BigDecimal percent) {
    super(order);
    if (percent.signum() < 0 || percent.compareTo(HUNDRED) > 0) {
      throw new IllegalArgumentException("percent must be between 0 and 100, was " + percent);
    }
    this.percent = percent;
  }

  @Override
  protected BigDecimal amountToTakeOff() {
    return order.total().multiply(percent).divide(HUNDRED);
  }

  @Override
  public String description() {
    return percent.stripTrailingZeros().toPlainString() + "% discount";
  }
}
