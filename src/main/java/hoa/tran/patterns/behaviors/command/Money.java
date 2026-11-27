/*
 * @(#)Money.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Small helpers so every amount in this example is rounded to cents the same way.
 *
 * @author hoa.tn
 * @since 1.0
 */
final class Money {

  static final BigDecimal ZERO = cents(BigDecimal.ZERO);

  private Money() {
  }

  static BigDecimal cents(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.HALF_UP);
  }
}
