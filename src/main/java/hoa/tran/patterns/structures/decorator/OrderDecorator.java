/*
 * @(#)OrderDecorator.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.decorator;
import java.math.BigDecimal;
/**
 * The base <em>decorator</em>. It implements {@link Order} and wraps another {@link Order}.
 * By default it passes every call through; subclasses change only what they need.
 *
 * @author hoa.tn
 * @since 1.0
 */
public abstract class OrderDecorator implements Order {
  protected final Order wrapped;
  protected OrderDecorator(Order wrapped) {
    this.wrapped = wrapped;
  }
  @Override
  public BigDecimal getTotal() {
    return wrapped.getTotal();
  }
  @Override
  public String describe() {
    return wrapped.describe();
  }
}
