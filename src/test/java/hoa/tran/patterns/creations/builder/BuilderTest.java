/*
 * @(#)BuilderTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Checks that the builders assemble, price and validate products correctly.
 *
 * @author hoa.tn
 * @since 1.0
 */
class BuilderTest {

  @Test
  void defaultPictureMatchesFactoryProduct() {
    PictureProduct product = PictureProduct.builder().build();
    assertEquals("Property photos", product.getName());
    assertEquals(15.0, product.getPrice());
    assertEquals("Shot, edited and colour-corrected 10 photos of 12 Harbour Street",
        product.produce("12 Harbour Street"));
  }

  @Test
  void pictureOptionsAddToPrice() {
    PictureProduct product = PictureProduct.builder()
        .photoCount(25)
        .blueSky()
        .windowDetails()
        .straightening()
        .build();
    // 15 base + 15 extra photos + 5 blue sky + 5 window details + 3 straightening
    assertEquals(43.0, product.getPrice());
    assertEquals(
        "Shot, edited and colour-corrected 25 photos (blue sky, window details, straightening)"
            + " of 12 Harbour Street",
        product.produce("12 Harbour Street"));
  }

  @Test
  void pictureRejectsInvalidPhotoCount() {
    assertThrows(IllegalStateException.class, () -> PictureProduct.builder().photoCount(0).build());
    assertThrows(IllegalStateException.class,
        () -> PictureProduct.builder().photoCount(101).build());
  }

  @Test
  void defaultFloorplanMatchesFactoryProduct() {
    FloorplanProduct product = FloorplanProduct.builder().build();
    assertEquals("2D floor plan", product.getName());
    assertEquals(40.0, product.getPrice());
    assertEquals("Measured and drew a 2D floor plan of 12 Harbour Street",
        product.produce("12 Harbour Street"));
  }

  @Test
  void floorplanOptionsChangeNameAndPrice() {
    FloorplanProduct product = FloorplanProduct.builder()
        .floors(2)
        .threeD()
        .furniture()
        .measurements()
        .build();
    assertEquals("3D floor plan", product.getName());
    // 40 base + 20 extra floor + 30 3D + 10 furniture + 5 measurements
    assertEquals(105.0, product.getPrice());
    assertEquals(
        "Measured and drew a 3D floor plan (2 floors, furniture, measurements)"
            + " of 12 Harbour Street",
        product.produce("12 Harbour Street"));
  }

  @Test
  void floorplanRejectsInvalidFloors() {
    assertThrows(IllegalStateException.class, () -> FloorplanProduct.builder().floors(0).build());
    assertThrows(IllegalStateException.class, () -> FloorplanProduct.builder().floors(11).build());
  }
}
