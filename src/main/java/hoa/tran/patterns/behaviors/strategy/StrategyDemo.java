/*
 * @(#)StrategyDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;
import java.util.List;

/**
 * Runs the Strategy example. The same order goes through the same {@link Checkout} four
 * times. Only the strategy changes, and so does the total.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class StrategyDemo {

  public static void main(String[] args) {
    Product photos = new Product("Property photos", new BigDecimal("15.00"));
    int properties = 4;

    List<PricingStrategy> strategies = List.of(
        new RegularPrice(),
        new PercentageDiscount(new BigDecimal("10")),
        new Voucher("WELCOME20", new BigDecimal("20"), new BigDecimal("50")),
        new BuyXGetYFreePromotion(3, 1));

    Checkout checkout = new Checkout();
    for (PricingStrategy strategy : strategies) {
      checkout.setStrategy(strategy);
      System.out.println(checkout.receipt(photos, properties));
    }

    System.out.println("Voucher below minimum spend:");
    checkout.setStrategy(new Voucher("WELCOME20", new BigDecimal("20"), new BigDecimal("50")));
    System.out.println(checkout.receipt(photos, 2));
  }
}
