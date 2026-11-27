/*
 * @(#)Order.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The receiver. It holds the order and the list of amounts taken off it. It knows how to add
 * or remove an {@link Adjustment}, but not what a voucher or a promotion is. That knowledge
 * lives in the commands.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class Order {

  private final Product product;
  private final int quantity;
  private final List<Adjustment> adjustments = new ArrayList<>();

  public Order(Product product, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("quantity must be at least 1, was " + quantity);
    }
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal subtotal() {
    return Money.cents(product.unitPrice().multiply(BigDecimal.valueOf(quantity)));
  }

  /** Subtotal minus every adjustment. The commands make sure this never goes below zero. */
  public BigDecimal total() {
    BigDecimal taken = adjustments.stream()
        .map(Adjustment::amount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    return Money.cents(subtotal().subtract(taken));
  }

  public List<Adjustment> getAdjustments() {
    return List.copyOf(adjustments);
  }

  void addAdjustment(Adjustment adjustment) {
    adjustments.add(adjustment);
  }

  void removeAdjustment(Adjustment adjustment) {
    adjustments.remove(adjustment);
  }

  public String describe() {
    StringBuilder text = new StringBuilder("%d x %s = %s EUR"
        .formatted(quantity, product.name(), subtotal()));
    for (Adjustment adjustment : adjustments) {
      text.append(", %s -%s EUR".formatted(adjustment.label(), adjustment.amount()));
    }
    return text.append(", total %s EUR".formatted(total())).toString();
  }
}
