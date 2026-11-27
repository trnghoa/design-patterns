/*
 * @(#)CustomerEmailNotifier.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Emails the customer on every status change, so they can follow their order.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class CustomerEmailNotifier implements OrderObserver {

  private final List<String> sentEmails = new ArrayList<>();

  @Override
  public void onStatusChanged(OrderEvent event) {
    String email = "To %s: order %s is now %s"
        .formatted(event.customer(), event.orderId(), event.status());
    sentEmails.add(email);
    System.out.println("  [email]     " + email);
  }

  public List<String> getSentEmails() {
    return List.copyOf(sentEmails);
  }
}
