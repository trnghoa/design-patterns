/*
 * @(#)OrderFacade.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
import java.math.BigDecimal;
/**
 * The <em>facade</em>. It gives callers one simple method, {@link #placeOrder}, and hides the
 * four services and the order they must be called in, including undoing the stock reservation
 * when payment fails.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class OrderFacade {
  static final BigDecimal UNIT_PRICE = new BigDecimal("5.00");
  private final InventoryService inventory;
  private final PaymentService payment;
  private final ShippingService shipping;
  private final NotificationService notification;
  public OrderFacade(InventoryService inventory, PaymentService payment,
      ShippingService shipping, NotificationService notification) {
    this.inventory = inventory;
    this.payment = payment;
    this.shipping = shipping;
    this.notification = notification;
  }
  public OrderResult placeOrder(String customerId, String productId, int quantity,
      String address) {
    if (!inventory.reserve(productId, quantity)) {
      notification.send(customerId, "Sorry, " + productId + " is out of stock.");
      return OrderResult.failed("Out of stock");
    }
    BigDecimal amount = UNIT_PRICE.multiply(BigDecimal.valueOf(quantity));
    if (!payment.charge(customerId, amount)) {
      inventory.release(productId, quantity);
      notification.send(customerId, "Your payment of $" + amount + " was declined.");
      return OrderResult.failed("Payment declined");
    }
    String trackingNumber = shipping.ship(productId, quantity, address);
    notification.send(customerId, "Your order is on its way. Tracking: " + trackingNumber);
    return OrderResult.placed(trackingNumber);
  }
}
