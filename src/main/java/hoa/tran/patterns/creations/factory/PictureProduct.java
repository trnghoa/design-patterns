/*
 * @(#)PictureProduct.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

import lombok.Getter;

/**
 * Edited property photos.
 *
 * @author hoa.tn
 * @since 1.0
 */
@Getter
public class PictureProduct implements Product {

  private final String name = "Property photos";
  private final double price = 15.0;

  @Override
  public String produce(String address) {
    return "Shot, edited and colour-corrected photos of " + address;
  }
}
