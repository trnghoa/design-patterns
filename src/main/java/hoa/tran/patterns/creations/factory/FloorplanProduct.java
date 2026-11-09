/*
 * @(#)FloorplanProduct.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

import lombok.Getter;

/**
 * 2D floor plan of a property.
 *
 * @author hoa.tn
 * @since 1.0
 */
@Getter
public class FloorplanProduct implements Product {

  private final String name = "2D floor plan";
  private final double price = 40.0;

  @Override
  public String produce(String address) {
    return "Measured and drew a 2D floor plan of " + address;
  }
}
