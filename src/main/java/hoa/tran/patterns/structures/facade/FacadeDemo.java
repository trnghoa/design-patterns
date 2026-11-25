/*
 * @(#)FacadeDemo.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
/**
 * Runs the Facade example. Each order is one call to {@link OrderFacade#placeOrder}; the caller
 * never talks to the inventory, payment, shipping or notification services directly.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class FacadeDemo {
  public static void main(String[] args) {
    InventoryService inventory = new InventoryService();
    inventory.addStock("PHOTO-PACK", 300);
    OrderFacade orders = new OrderFacade(
        inventory, new PaymentService(), new ShippingService(), new NotificationService());

    place(orders, "Successful order:", "alice", 10);
    place(orders, "Out of stock (only 290 left):", "bob", 500);
    place(orders, "Payment declined ($1,250 is over the $1,000 limit):", "carol", 250);
    System.out.println("Stock left: " + inventory.getStock("PHOTO-PACK"));
  }

  private static void place(OrderFacade orders, String title, String customerId, int quantity) {
    System.out.println(title);
    OrderResult result = orders.placeOrder(customerId, "PHOTO-PACK", quantity, "12 Harbour Street");
    System.out.println("  => " + result.message()
        + (result.success() ? " (" + result.trackingNumber() + ")" : ""));
  }
}
