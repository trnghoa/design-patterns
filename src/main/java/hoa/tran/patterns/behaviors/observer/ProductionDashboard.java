/*
 * @(#)ProductionDashboard.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shows the latest status of every order for the production team.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ProductionDashboard implements OrderObserver {

  private final Map<String, OrderStatus> latestStatus = new LinkedHashMap<>();

  @Override
  public void onStatusChanged(OrderEvent event) {
    latestStatus.put(event.orderId(), event.status());
    System.out.println("  [dashboard] " + latestStatus);
  }

  public OrderStatus statusOf(String orderId) {
    return latestStatus.get(orderId);
  }
}
