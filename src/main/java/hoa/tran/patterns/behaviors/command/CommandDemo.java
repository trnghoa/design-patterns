/*
 * @(#)CommandDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * Runs the Command example. Compare with {@code StrategyDemo}: there the checkout uses one
 * offer at a time; here each offer is an action that can be stacked, undone, redone and
 * logged.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class CommandDemo {

  public static void main(String[] args) {
    Product photos = new Product("Property photos", new BigDecimal("15.00"));
    Order order = new Order(photos, 8);
    CheckoutSession session = new CheckoutSession();
    System.out.println("Start:        " + order.describe());

    session.apply(new ApplyPromotionCommand(order, 3, 1));
    System.out.println("+ promotion:  " + order.describe());

    session.apply(new ApplyVoucherCommand(order, "WELCOME20", new BigDecimal("20"),
        new BigDecimal("50")));
    System.out.println("+ voucher:    " + order.describe());

    session.apply(new ApplyDiscountCommand(order, new BigDecimal("10")));
    System.out.println("+ discount:   " + order.describe());

    session.undo();
    System.out.println("undo:         " + order.describe());

    session.redo();
    System.out.println("redo:         " + order.describe());

    Order smallOrder = new Order(photos, 2);
    try {
      new CheckoutSession().apply(new ApplyVoucherCommand(smallOrder, "WELCOME20", new BigDecimal("20"),
          new BigDecimal("50")));
    } catch (IllegalStateException refused) {
      System.out.println("Refused:      " + refused.getMessage());
    }

    System.out.println("Audit log:");
    session.getAuditLog().forEach(line -> System.out.println("  " + line));
  }
}
