/*
 * @(#)DecoratorTest.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * Checks the unit price and the 5 percent discount for orders over 20 dollars.
 *
 * @author hoa.tn
 * @since 1.0
 */
class DecoratorTest {

  private static Order discounted(int quantity) {
    return new VolumeDiscountDecorator(new ProductOrder(quantity));
  }

  @Test
  void plainOrderIsFiveDollarsPerUnit() {
    assertEquals(new BigDecimal("25.00"), new ProductOrder(5).getTotal());
  }

  @Test
  void orderUnderTwentyDollarsGetsNoDiscount() {
    assertEquals(new BigDecimal("15.00"), discounted(3).getTotal());
  }

  @Test
  void orderOfExactlyTwentyDollarsGetsNoDiscount() {
    assertEquals(new BigDecimal("20.00"), discounted(4).getTotal());
  }

  @Test
  void orderOverTwentyDollarsGetsFivePercentOff() {
    assertEquals(new BigDecimal("23.75"), discounted(5).getTotal());
    assertEquals(new BigDecimal("47.50"), discounted(10).getTotal());
  }

  @Test
  void decoratorDoesNotChangeTheWrappedOrder() {
    Order plain = new ProductOrder(5);
    new VolumeDiscountDecorator(plain).getTotal();
    assertEquals(new BigDecimal("25.00"), plain.getTotal());
  }

  @Test
  void negativeQuantityIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> new ProductOrder(-1));
  }
}
