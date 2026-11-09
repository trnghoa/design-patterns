/*
 * @(#)ProductStore.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

/**
 *
 * @author hoa.tn
 * @since 1.0
 */
public class ProductStore {
  ProductCreator productCreator;
  public ProductStore(ProductCreator productCreator) {
    this.productCreator = productCreator;
  }

  public String order(String address) {
    return  productCreator.order(address);
  }
}
