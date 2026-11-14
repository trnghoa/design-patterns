/*
 * @(#)BuilderDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.builder;

import java.util.List;

/**
 * Runs the Builder example. Compare with {@code FactoryMethodDemo}: there the creator
 * decides <em>which</em> product to make; here the caller decides <em>how</em> each
 * product is put together.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class BuilderDemo {

  public static void main(String[] args) {
    List<Product> products = List.of(
        // Defaults: the same products the factory example creates.
        PictureProduct.builder().build(),
        FloorplanProduct.builder().build(),
        // Configured step by step.
        PictureProduct.builder()
            .photoCount(25)
            .blueSky()
            .windowDetails()
            .build(),
        FloorplanProduct.builder()
            .floors(2)
            .threeD()
            .furniture()
            .measurements()
            .build());

    for (Product product : products) {
      System.out.printf("%s (%.2f EUR): %s%n",
          product.getName(), product.getPrice(), product.produce("12 Harbour Street"));
    }
  }
}
