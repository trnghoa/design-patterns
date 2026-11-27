/*
 * @(#)OrderObserver.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

/**
 * The observer. Anything that wants to react when an order changes status implements this
 * and subscribes to an {@link OrderTracker}. It has one method, so a lambda also works.
 *
 * @author hoa.tn
 * @since 1.0
 */
@FunctionalInterface
public interface OrderObserver {

  void onStatusChanged(OrderEvent event);
}
