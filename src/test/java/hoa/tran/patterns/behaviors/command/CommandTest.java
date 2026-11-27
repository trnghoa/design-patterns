/*
 * @(#)CommandTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Checks each command, stacking several of them, and undo, redo and the audit log.
 *
 * @author hoa.tn
 * @since 1.0
 */
class CommandTest {

  private static final Product PHOTOS = new Product("Property photos", new BigDecimal("15.00"));

  private static BigDecimal eur(String amount) {
    return new BigDecimal(amount);
  }

  private static ApplyVoucherCommand welcome20(Order order) {
    return new ApplyVoucherCommand(order, "WELCOME20", eur("20"), eur("50"));
  }

  @Test
  void discountTakesAPercentageOff() {
    Order order = new Order(PHOTOS, 4);
    new CheckoutSession().apply(new ApplyDiscountCommand(order, eur("10")));
    assertEquals(eur("54.00"), order.total());
  }

  @Test
  void voucherTakesAFixedAmountOff() {
    Order order = new Order(PHOTOS, 4);
    new CheckoutSession().apply(welcome20(order));
    assertEquals(eur("40.00"), order.total());
  }

  @Test
  void voucherBelowMinimumSpendIsRefusedAndNotRecorded() {
    Order order = new Order(PHOTOS, 2);
    CheckoutSession session = new CheckoutSession();

    assertThrows(IllegalStateException.class, () -> session.apply(welcome20(order)));

    assertEquals(eur("30.00"), order.total());
    assertTrue(session.getAuditLog().isEmpty());
    assertFalse(session.undo());
  }

  @Test
  void promotionGivesFreeUnitsPerFullGroup() {
    Order order = new Order(PHOTOS, 7);
    new CheckoutSession().apply(new ApplyPromotionCommand(order, 3, 1));
    assertEquals(eur("90.00"), order.total());
  }

  @Test
  void commandsStackInTheOrderTheyAreApplied() {
    Order order = new Order(PHOTOS, 8);
    CheckoutSession session = new CheckoutSession();

    session.apply(new ApplyPromotionCommand(order, 3, 1));
    session.apply(welcome20(order));
    session.apply(new ApplyDiscountCommand(order, eur("10")));

    // 120 - 30 (2 free) = 90, - 20 voucher = 70, - 10% of 70 = 63
    assertEquals(eur("63.00"), order.total());
    assertEquals(List.of(
            new Adjustment("promotion buy 3 get 1 free", eur("30.00")),
            new Adjustment("voucher WELCOME20", eur("20.00")),
            new Adjustment("10% discount", eur("7.00"))),
        order.getAdjustments());
  }

  @Test
  void undoAndRedoRestoreTheTotals() {
    Order order = new Order(PHOTOS, 8);
    CheckoutSession session = new CheckoutSession();
    session.apply(new ApplyPromotionCommand(order, 3, 1));
    session.apply(welcome20(order));

    assertTrue(session.undo());
    assertEquals(eur("90.00"), order.total());
    assertTrue(session.undo());
    assertEquals(eur("120.00"), order.total());
    assertFalse(session.undo());

    assertTrue(session.redo());
    assertEquals(eur("90.00"), order.total());
    assertTrue(session.redo());
    assertEquals(eur("70.00"), order.total());
    assertFalse(session.redo());
  }

  @Test
  void newCommandClearsRedo() {
    Order order = new Order(PHOTOS, 8);
    CheckoutSession session = new CheckoutSession();
    session.apply(welcome20(order));
    session.undo();

    session.apply(new ApplyDiscountCommand(order, eur("10")));

    assertFalse(session.redo());
    assertEquals(eur("108.00"), order.total());
  }

  @Test
  void totalNeverGoesBelowZero() {
    Order order = new Order(PHOTOS, 1);
    CheckoutSession session = new CheckoutSession();
    session.apply(new ApplyVoucherCommand(order, "BIG100", eur("100"), eur("0")));
    session.apply(new ApplyDiscountCommand(order, eur("50")));
    assertEquals(eur("0.00"), order.total());
  }

  @Test
  void sameCommandCannotBeAppliedTwice() {
    Order order = new Order(PHOTOS, 4);
    ApplyVoucherCommand voucher = welcome20(order);
    voucher.execute();
    assertThrows(IllegalStateException.class, voucher::execute);
  }

  @Test
  void auditLogRecordsEveryAction() {
    Order order = new Order(PHOTOS, 8);
    CheckoutSession session = new CheckoutSession();
    session.apply(welcome20(order));
    session.undo();
    session.redo();

    assertEquals(List.of(
            "APPLY voucher WELCOME20",
            "UNDO  voucher WELCOME20",
            "REDO  voucher WELCOME20"),
        session.getAuditLog());
  }
}
