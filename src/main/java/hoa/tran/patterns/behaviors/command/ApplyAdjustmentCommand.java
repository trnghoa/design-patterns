/*
 * @(#)ApplyAdjustmentCommand.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * Shared execute and undo logic for the discount, voucher and promotion commands. Each
 * subclass only says how much to take off; this class adds that amount to the order and
 * remembers it, so {@link #undo()} can remove exactly the same line.
 *
 * @author hoa.tn
 * @since 1.0
 */
public abstract class ApplyAdjustmentCommand implements OrderCommand {

  protected final Order order;
  private Adjustment applied;

  protected ApplyAdjustmentCommand(Order order) {
    this.order = order;
  }

  /** How much to take off, based on the order as it is right now. */
  protected abstract BigDecimal amountToTakeOff();

  @Override
  public void execute() {
    if (applied != null) {
      throw new IllegalStateException(description() + " is already applied");
    }
    // Never take off more than is left, so the total cannot go below zero.
    BigDecimal amount = Money.cents(amountToTakeOff()).max(Money.ZERO).min(order.total());
    applied = new Adjustment(description(), amount);
    order.addAdjustment(applied);
  }

  @Override
  public void undo() {
    if (applied == null) {
      throw new IllegalStateException(description() + " is not applied");
    }
    order.removeAdjustment(applied);
    applied = null;
  }
}
