/*
 * @(#)ProductCreator.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

/**
 * The creator. It holds the shared ordering logic but leaves the choice of which
 * {@link Product} to make to its subclasses, through {@link #createProduct()}.
 *
 * @author hoa.tn
 * @since 1.0
 */
public abstract class ProductCreator {

  /** The factory method: each subclass decides which concrete product to create. */
  protected abstract Product createProduct();

  /**
   * Shared business logic. It works with any product without knowing its concrete class,
   * so adding a new product never requires changing this method.
   */
  public String order(String address) {
    Product product = createProduct();
    return "%s (%.2f EUR): %s".formatted(
        product.getName(), product.getPrice(), product.produce(address));
  }
}
