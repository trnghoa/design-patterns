/*
 * @(#)OrderEvent.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

/**
 * What happened: an order moved to a new status. The tracker sends this to every observer.
 *
 * @author hoa.tn
 * @since 1.0
 */
public record OrderEvent(String orderId, String customer, OrderStatus status) {
}
