/*
 * @(#)Order.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.decorator;
import java.math.BigDecimal;
/**
 * The <em>component</em>: anything that has a total price. The plain order and every decorator
 * implement this, so callers cannot tell them apart.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface Order {
  /** Total price in dollars, rounded to cents. */
  BigDecimal getTotal();
  /** Human-readable description of how the total was made. */
  String describe();
}
