/*
 * @(#)PriceList.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.singleton;

import java.util.Map;

/**
 * The company's one official price list. There is exactly one instance, created by the class
 * itself, and every caller reaches it through {@link #getInstance()}.
 *
 * <p>The instance is created when the JVM first loads the class. Class loading is
 * thread-safe, so no locking is needed and two threads can never create two copies.
 *
 * @author hoa.tn
 * @since 1.0
 */
public final class PriceList {

  private static final PriceList INSTANCE = new PriceList();

  private final Map<String, Double> prices = Map.of(
      "Property photos", 15.0,
      "2D floor plan", 40.0,
      "3D floor plan", 70.0,
      "Property video", 120.0);

  /** Private, so no other class can call {@code new PriceList()}. */
  private PriceList() {
    System.out.println("  [singleton] PriceList created");
  }

  public static PriceList getInstance() {
    return INSTANCE;
  }

  public double priceOf(String productName) {
    Double price = prices.get(productName);
    if (price == null) {
      throw new IllegalArgumentException("No price for product: " + productName);
    }
    return price;
  }
}
