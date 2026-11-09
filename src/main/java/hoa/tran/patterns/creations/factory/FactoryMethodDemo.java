/*
 * @(#)FactoryMethodDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

import java.util.List;

/**
 * Runs the Factory Method example.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class FactoryMethodDemo {

  public static void main(String[] args) {
    List<ProductCreator> creators =
        List.of(new PictureProductCreator(), new FloorplanProductCreator());

    for (ProductCreator creator : creators) {
      ProductStore productStore = new ProductStore(creator);
      System.out.println(productStore.order("12 Harbour Street"));
    }
  }
}
