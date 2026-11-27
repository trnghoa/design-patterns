/*
 * @(#)OrderTracker.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The subject. It keeps a list of observers and tells all of them whenever an order changes
 * status. It does not know what the observers do: sending email, creating an invoice and
 * updating a dashboard all look the same from here.
 *
 * <p>The list is a {@link CopyOnWriteArrayList}, so an observer can subscribe or unsubscribe
 * while a notification is in progress without breaking the loop.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class OrderTracker {

  private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();

  /** Adds an observer. Subscribing the same observer twice has no extra effect. */
  public void subscribe(OrderObserver observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  public void unsubscribe(OrderObserver observer) {
    observers.remove(observer);
  }

  /** Records the new status and notifies every observer, in the order they subscribed. */
  public void updateStatus(String orderId, String customer, OrderStatus status) {
    System.out.printf("[tracker] %s -> %s%n", orderId, status);
    OrderEvent event = new OrderEvent(orderId, customer, status);
    for (OrderObserver observer : observers) {
      observer.onStatusChanged(event);
    }
  }
}
