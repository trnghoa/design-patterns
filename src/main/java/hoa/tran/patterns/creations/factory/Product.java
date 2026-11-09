/*
 * @(#)Product.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

/**
 * The product the factory method creates. Callers only ever see this interface,
 * never the concrete class.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface Product {

  String getName();

  double getPrice();

  /** Does the product-specific work and describes the result. */
  String produce(String address);
}
