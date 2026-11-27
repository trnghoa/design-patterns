/*
 * @(#)ApplyPromotionCommand.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * Promotion: buy X, get Y free. With "buy 3, get 1 free", every full group of 4 units has 1
 * unit free: 4 units pay for 3, 8 units pay for 6, 7 units pay for 6.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ApplyPromotionCommand extends ApplyAdjustmentCommand {

  private final int buy;
  private final int free;

  public ApplyPromotionCommand(Order order, int buy, int free) {
    super(order);
    if (buy < 1 || free < 1) {
      throw new IllegalArgumentException("buy and free must be at least 1");
    }
    this.buy = buy;
    this.free = free;
  }

  @Override
  protected BigDecimal amountToTakeOff() {
    int freeUnits = (order.getQuantity() / (buy + free)) * free;
    return order.getProduct().unitPrice().multiply(BigDecimal.valueOf(freeUnits));
  }

  @Override
  public String description() {
    return "promotion buy %d get %d free".formatted(buy, free);
  }
}
