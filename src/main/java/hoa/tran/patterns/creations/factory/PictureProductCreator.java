/*
 * @(#)PictureProductCreator.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.factory;

/**
 * Creates {@link PictureProduct}s.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class PictureProductCreator extends ProductCreator {

  @Override
  protected Product createProduct() {
    return new PictureProduct();
  }
}
