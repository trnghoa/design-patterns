/*
 * @(#)PrototypeDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.prototype;

import java.util.List;

/**
 * Runs the Prototype example. Two standard presets are registered once. Each customer then
 * gets a copy and adjusts it, and the standard presets stay unchanged.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class PrototypeDemo {

  public static void main(String[] args) {
    PresetRegistry registry = new PresetRegistry();
    registry.register("standard",
        new EditingPreset("Standard", "Natural", List.of("HDR merge", "white balance")));
    registry.register("luxury",
        new EditingPreset("Luxury", "Warm bright",
            List.of("HDR merge", "white balance", "window details", "blue sky")));

    EditingPreset nordicHomes = registry.get("luxury")
        .rename("Nordic Homes - Luxury")
        .colourStyle("Cool bright")
        .addService("straightening");

    EditingPreset cityRentals = registry.get("standard")
        .rename("City Rentals - Standard")
        .addService("blur house numbers");

    System.out.println("Customer presets (copies):");
    System.out.println("  " + nordicHomes);
    System.out.println("  " + cityRentals);

    System.out.println("Registered presets (unchanged):");
    System.out.println("  " + registry.get("luxury"));
    System.out.println("  " + registry.get("standard"));
  }
}
