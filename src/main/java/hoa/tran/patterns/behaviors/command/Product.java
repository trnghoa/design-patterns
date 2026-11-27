/*
 * @(#)Product.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * A product the customer can order, with its price per unit in EUR. Same shape as the
 * strategy package's {@code Product}, so the two examples can be compared side by side.
 *
 * @author hoa.tn
 * @since 1.0
 */
public record Product(String name, BigDecimal unitPrice) {

  public Product {
    if (unitPrice.signum() < 0) {
      throw new IllegalArgumentException("unitPrice must not be negative, was " + unitPrice);
    }
  }
}
