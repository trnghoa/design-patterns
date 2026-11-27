/*
 * @(#)BuyXGetYFreePromotion.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import java.math.BigDecimal;

/**
 * Promotion: buy X, get Y free. With "buy 3, get 1 free", every full group of 4 units has 1
 * unit free: 4 units pay for 3, 8 units pay for 6, 7 units pay for 6.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class BuyXGetYFreePromotion implements PricingStrategy {

  private final int buy;
  private final int free;

  public BuyXGetYFreePromotion(int buy, int free) {
    if (buy < 1 || free < 1) {
      throw new IllegalArgumentException("buy and free must be at least 1");
    }
    this.buy = buy;
    this.free = free;
  }

  @Override
  public BigDecimal discountFor(Product product, int quantity) {
    int freeUnits = (quantity / (buy + free)) * free;
    return Money.cents(product.unitPrice().multiply(BigDecimal.valueOf(freeUnits)));
  }

  @Override
  public String label() {
    return "promotion buy %d get %d free".formatted(buy, free);
  }
}
