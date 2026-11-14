/*
 * @(#)Product.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.builder;

/**
 * The product a builder assembles. Same shape as the factory package's {@code Product},
 * so the two creation styles can be compared side by side.
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
