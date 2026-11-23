/*
 * @(#)ProductOrder.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.decorator;
import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * The <em>concrete component</em>: a number of units at 5 dollars each, with no discount.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ProductOrder implements Order {
  static final BigDecimal UNIT_PRICE = new BigDecimal("5.00");
  private final int quantity;
  public ProductOrder(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("quantity must not be negative, was " + quantity);
    }
    this.quantity = quantity;
  }
  @Override
  public BigDecimal getTotal() {
    return UNIT_PRICE.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
  }
  @Override
  public String describe() {
    return "%d x $%s = $%s".formatted(quantity, UNIT_PRICE, getTotal());
  }
}
