/*
 * @(#)ObserverDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

/**
 * Runs the Observer example. One tracker, several observers. Each status change reaches every
 * subscribed observer, and an observer that unsubscribes stops hearing about changes.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ObserverDemo {

  public static void main(String[] args) {
    OrderTracker tracker = new OrderTracker();
    CustomerEmailNotifier email = new CustomerEmailNotifier();

    tracker.subscribe(email);
    tracker.subscribe(new InvoiceService());
    tracker.subscribe(new ProductionDashboard());
    // A lambda works too, because OrderObserver has only one method.
    tracker.subscribe(event -> {
      if (event.status() == OrderStatus.DELIVERED) {
        System.out.println("  [webhook]   POST https://client.example/hooks/" + event.orderId());
      }
    });

    tracker.updateStatus("ORD-1001", "Nordic Homes", OrderStatus.RECEIVED);
    tracker.updateStatus("ORD-1001", "Nordic Homes", OrderStatus.IN_EDITING);

    System.out.println("Customer turned off email updates");
    tracker.unsubscribe(email);

    tracker.updateStatus("ORD-1001", "Nordic Homes", OrderStatus.QUALITY_CHECK);
    tracker.updateStatus("ORD-1001", "Nordic Homes", OrderStatus.DELIVERED);

    System.out.println("Emails sent: " + email.getSentEmails().size());
  }
}
