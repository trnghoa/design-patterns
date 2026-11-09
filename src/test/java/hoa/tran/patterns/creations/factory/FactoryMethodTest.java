/*
 * @(#)FactoryMethodTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Checks that each creator builds the right product.
 *
 * @author hoa.tn
 * @since 1.0
 */
class FactoryMethodTest {

  @Test
  void pictureCreatorMakesPictureProduct() {
    Product product = new PictureProductCreator().createProduct();
    assertInstanceOf(PictureProduct.class, product);
    assertEquals(15.0, product.getPrice());
  }

  @Test
  void floorplanCreatorMakesFloorplanProduct() {
    Product product = new FloorplanProductCreator().createProduct();
    assertInstanceOf(FloorplanProduct.class, product);
    assertEquals(40.0, product.getPrice());
  }

  @Test
  void orderUsesTheCreatedProduct() {
    String result = new FloorplanProductCreator().order("12 Harbour Street");
    assertTrue(result.startsWith("2D floor plan (40.00 EUR)"));
    assertTrue(result.endsWith("12 Harbour Street"));
  }
}
