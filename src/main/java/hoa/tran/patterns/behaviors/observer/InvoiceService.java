/*
 * @(#)InvoiceService.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an invoice once an order is delivered. It receives every event but ignores all
 * statuses except {@link OrderStatus#DELIVERED}.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class InvoiceService implements OrderObserver {

  private final List<String> invoicedOrders = new ArrayList<>();

  @Override
  public void onStatusChanged(OrderEvent event) {
    if (event.status() != OrderStatus.DELIVERED) {
      return;
    }
    invoicedOrders.add(event.orderId());
    System.out.printf("  [invoice]   invoice created for %s (%s)%n",
        event.orderId(), event.customer());
  }

  public List<String> getInvoicedOrders() {
    return List.copyOf(invoicedOrders);
  }
}
