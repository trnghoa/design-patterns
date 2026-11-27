/*
 * @(#)ObserverTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Checks that the tracker notifies the right observers, in order, and stops notifying an
 * observer once it unsubscribes.
 *
 * @author hoa.tn
 * @since 1.0
 */
class ObserverTest {

  private static final String ORDER = "ORD-1001";
  private static final String CUSTOMER = "Nordic Homes";

  @Test
  void everySubscribedObserverIsNotified() {
    OrderTracker tracker = new OrderTracker();
    CustomerEmailNotifier email = new CustomerEmailNotifier();
    ProductionDashboard dashboard = new ProductionDashboard();
    tracker.subscribe(email);
    tracker.subscribe(dashboard);

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.IN_EDITING);

    assertEquals(List.of("To Nordic Homes: order ORD-1001 is now IN_EDITING"),
        email.getSentEmails());
    assertEquals(OrderStatus.IN_EDITING, dashboard.statusOf(ORDER));
  }

  @Test
  void invoiceIsCreatedOnlyWhenDelivered() {
    OrderTracker tracker = new OrderTracker();
    InvoiceService invoices = new InvoiceService();
    tracker.subscribe(invoices);

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.RECEIVED);
    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.QUALITY_CHECK);
    assertTrue(invoices.getInvoicedOrders().isEmpty());

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.DELIVERED);
    assertEquals(List.of(ORDER), invoices.getInvoicedOrders());
  }

  @Test
  void unsubscribedObserverStopsReceivingEvents() {
    OrderTracker tracker = new OrderTracker();
    CustomerEmailNotifier email = new CustomerEmailNotifier();
    tracker.subscribe(email);

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.RECEIVED);
    tracker.unsubscribe(email);
    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.DELIVERED);

    assertEquals(1, email.getSentEmails().size());
  }

  @Test
  void observersAreNotifiedInSubscriptionOrder() {
    OrderTracker tracker = new OrderTracker();
    List<String> calls = new ArrayList<>();
    tracker.subscribe(event -> calls.add("first"));
    tracker.subscribe(event -> calls.add("second"));

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.RECEIVED);

    assertEquals(List.of("first", "second"), calls);
  }

  @Test
  void subscribingTwiceNotifiesOnce() {
    OrderTracker tracker = new OrderTracker();
    CustomerEmailNotifier email = new CustomerEmailNotifier();
    tracker.subscribe(email);
    tracker.subscribe(email);

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.RECEIVED);

    assertEquals(1, email.getSentEmails().size());
  }

  @Test
  void observerCanUnsubscribeWhileBeingNotified() {
    OrderTracker tracker = new OrderTracker();
    List<OrderStatus> received = new ArrayList<>();
    OrderObserver oneShot = new OrderObserver() {
      @Override
      public void onStatusChanged(OrderEvent event) {
        received.add(event.status());
        tracker.unsubscribe(this);
      }
    };
    CustomerEmailNotifier email = new CustomerEmailNotifier();
    tracker.subscribe(oneShot);
    tracker.subscribe(email);

    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.RECEIVED);
    tracker.updateStatus(ORDER, CUSTOMER, OrderStatus.DELIVERED);

    assertEquals(List.of(OrderStatus.RECEIVED), received);
    assertEquals(2, email.getSentEmails().size());
  }
}
