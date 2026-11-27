/*
 * @(#)ApplyVoucherCommand.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * Voucher: a code worth a fixed amount, e.g. {@code WELCOME20} takes 20 EUR off. It is
 * refused if the order subtotal is below the minimum spend.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ApplyVoucherCommand extends ApplyAdjustmentCommand {

  private final String code;
  private final BigDecimal amount;
  private final BigDecimal minimumSpend;

  public ApplyVoucherCommand(Order order, String code, BigDecimal amount,
      BigDecimal minimumSpend) {
    super(order);
    if (amount.signum() < 0 || minimumSpend.signum() < 0) {
      throw new IllegalArgumentException("amount and minimumSpend must not be negative");
    }
    this.code = code;
    this.amount = Money.cents(amount);
    this.minimumSpend = Money.cents(minimumSpend);
  }

  @Override
  public void execute() {
    if (order.subtotal().compareTo(minimumSpend) < 0) {
      throw new IllegalStateException("voucher %s needs a minimum spend of %s EUR, order is %s EUR"
          .formatted(code, minimumSpend, order.subtotal()));
    }
    super.execute();
  }

  @Override
  protected BigDecimal amountToTakeOff() {
    return amount;
  }

  @Override
  public String description() {
    return "voucher " + code;
  }
}
