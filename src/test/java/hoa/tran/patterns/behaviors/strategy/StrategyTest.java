/*
 * @(#)StrategyTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * Checks each pricing strategy, and that {@link Checkout} uses whichever one it is given.
 *
 * @author hoa.tn
 * @since 1.0
 */
class StrategyTest {

  private static final Product PHOTOS = new Product("Property photos", new BigDecimal("15.00"));

  private static BigDecimal eur(String amount) {
    return new BigDecimal(amount);
  }

  private static BigDecimal totalWith(PricingStrategy strategy, int quantity) {
    Checkout checkout = new Checkout();
    checkout.setStrategy(strategy);
    return checkout.total(PHOTOS, quantity);
  }

  @Test
  void regularPriceIsTheDefault() {
    assertEquals(eur("60.00"), new Checkout().total(PHOTOS, 4));
  }

  @Test
  void percentageDiscountTakesAShareOff() {
    assertEquals(eur("54.00"), totalWith(new PercentageDiscount(eur("10")), 4));
    assertEquals(eur("0.00"), totalWith(new PercentageDiscount(eur("100")), 4));
  }

  @Test
  void percentageDiscountRejectsInvalidPercent() {
    assertThrows(IllegalArgumentException.class, () -> new PercentageDiscount(eur("-1")));
    assertThrows(IllegalArgumentException.class, () -> new PercentageDiscount(eur("101")));
  }

  @Test
  void voucherTakesFixedAmountOffWhenMinimumSpendIsMet() {
    Voucher voucher = new Voucher("WELCOME20", eur("20"), eur("50"));
    assertEquals(eur("40.00"), totalWith(voucher, 4));
    // 2 x 15 = 30, below the 50 minimum spend.
    assertEquals(eur("30.00"), totalWith(voucher, 2));
  }

  @Test
  void voucherNeverMakesTheTotalNegative() {
    assertEquals(eur("0.00"), totalWith(new Voucher("BIG100", eur("100"), eur("0")), 1));
  }

  @Test
  void promotionGivesFreeUnitsPerFullGroup() {
    BuyXGetYFreePromotion buy3Get1 = new BuyXGetYFreePromotion(3, 1);
    assertEquals(eur("45.00"), totalWith(buy3Get1, 3));
    assertEquals(eur("45.00"), totalWith(buy3Get1, 4));
    assertEquals(eur("90.00"), totalWith(buy3Get1, 7));
    assertEquals(eur("90.00"), totalWith(buy3Get1, 8));
  }

  @Test
  void checkoutUsesTheStrategyItHoldsNow() {
    Checkout checkout = new Checkout();
    checkout.setStrategy(new PercentageDiscount(eur("10")));
    assertEquals(eur("54.00"), checkout.total(PHOTOS, 4));

    checkout.setStrategy(new BuyXGetYFreePromotion(3, 1));
    assertEquals(eur("45.00"), checkout.total(PHOTOS, 4));

    checkout.setStrategy(null);
    assertEquals(eur("60.00"), checkout.total(PHOTOS, 4));
  }

  @Test
  void newStrategyNeedsNoChangeToCheckout() {
    PricingStrategy flatFive = new PricingStrategy() {
      @Override
      public BigDecimal discountFor(Product product, int quantity) {
        return eur("5");
      }

      @Override
      public String label() {
        return "flat 5 EUR off";
      }
    };
    assertEquals(eur("55.00"), totalWith(flatFive, 4));
  }

  @Test
  void receiptShowsTheStrategyUsed() {
    Checkout checkout = new Checkout();
    checkout.setStrategy(new Voucher("WELCOME20", eur("20"), eur("50")));
    assertEquals("4 x Property photos @ 15.00 EUR = 60.00 EUR,"
            + " voucher WELCOME20 (20.00 EUR off, min. spend 50.00 EUR) -20.00 EUR,"
            + " total 40.00 EUR",
        checkout.receipt(PHOTOS, 4));
  }

  @Test
  void checkoutRejectsInvalidQuantity() {
    assertThrows(IllegalArgumentException.class, () -> new Checkout().total(PHOTOS, 0));
  }
}
