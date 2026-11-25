/*
 * @(#)FacadeTest.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Checks that one call to the facade runs the services in the right order, and leaves stock
 * unchanged when an order fails.
 *
 * @author hoa.tn
 * @since 1.0
 */
class FacadeTest {

  private static final String PRODUCT = "PHOTO-PACK";

  private InventoryService inventory;
  private OrderFacade orders;

  @BeforeEach
  void setUp() {
    inventory = new InventoryService();
    inventory.addStock(PRODUCT, 300);
    orders = new OrderFacade(
        inventory, new PaymentService(), new ShippingService(), new NotificationService());
  }

  @Test
  void successfulOrderReducesStockAndReturnsTracking() {
    OrderResult result = orders.placeOrder("alice", PRODUCT, 10, "12 Harbour Street");
    assertTrue(result.success());
    assertEquals("TRK-1001", result.trackingNumber());
    assertEquals(290, inventory.getStock(PRODUCT));
  }

  @Test
  void outOfStockOrderFailsAndKeepsStock() {
    OrderResult result = orders.placeOrder("bob", PRODUCT, 500, "12 Harbour Street");
    assertFalse(result.success());
    assertEquals("Out of stock", result.message());
    assertNull(result.trackingNumber());
    assertEquals(300, inventory.getStock(PRODUCT));
  }

  @Test
  void declinedPaymentReleasesReservedStock() {
    OrderResult result = orders.placeOrder("carol", PRODUCT, 250, "12 Harbour Street");
    assertFalse(result.success());
    assertEquals("Payment declined", result.message());
    assertEquals(300, inventory.getStock(PRODUCT));
  }

  @Test
  void eachSuccessfulOrderGetsItsOwnTrackingNumber() {
    String first = orders.placeOrder("alice", PRODUCT, 1, "A").trackingNumber();
    String second = orders.placeOrder("dave", PRODUCT, 1, "B").trackingNumber();
    assertEquals("TRK-1001", first);
    assertEquals("TRK-1002", second);
  }
}
