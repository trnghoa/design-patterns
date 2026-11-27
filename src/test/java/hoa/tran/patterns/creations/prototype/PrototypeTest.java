/*
 * @(#)PrototypeTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.prototype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Checks that copies are independent of their prototype and of each other.
 *
 * @author hoa.tn
 * @since 1.0
 */
class PrototypeTest {

  private static EditingPreset standard() {
    return new EditingPreset("Standard", "Natural", List.of("HDR merge", "white balance"));
  }

  @Test
  void copyHasSameValuesButIsANewObject() {
    EditingPreset original = standard();
    EditingPreset copy = original.copy();

    assertNotSame(original, copy);
    assertEquals(original.getName(), copy.getName());
    assertEquals(original.getColourStyle(), copy.getColourStyle());
    assertEquals(original.getServices(), copy.getServices());
  }

  @Test
  void changingTheCopyLeavesTheOriginalUnchanged() {
    EditingPreset original = standard();
    original.copy()
        .rename("Custom")
        .colourStyle("Warm")
        .addService("blue sky")
        .removeService("HDR merge");

    assertEquals("Standard", original.getName());
    assertEquals("Natural", original.getColourStyle());
    // Fails if copy() shares the services list (shallow copy).
    assertEquals(List.of("HDR merge", "white balance"), original.getServices());
  }

  @Test
  void registryHandsOutIndependentCopies() {
    PresetRegistry registry = new PresetRegistry();
    registry.register("standard", standard());

    EditingPreset first = registry.get("standard").addService("straightening");
    EditingPreset second = registry.get("standard");

    assertNotSame(first, second);
    assertEquals(List.of("HDR merge", "white balance", "straightening"), first.getServices());
    assertEquals(List.of("HDR merge", "white balance"), second.getServices());
  }

  @Test
  void registryIsNotAffectedByChangesToTheRegisteredObject() {
    PresetRegistry registry = new PresetRegistry();
    EditingPreset preset = standard();
    registry.register("standard", preset);

    preset.addService("blue sky");

    assertEquals(List.of("HDR merge", "white balance"), registry.get("standard").getServices());
  }

  @Test
  void registryRejectsUnknownKey() {
    assertThrows(IllegalArgumentException.class, () -> new PresetRegistry().get("premium"));
  }
}
