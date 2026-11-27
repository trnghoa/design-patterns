/*
 * @(#)PresetRegistry.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps ready-made presets under a name and hands out copies of them. The stored originals
 * are never given out, so no caller can change them.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class PresetRegistry {

  private final Map<String, EditingPreset> prototypes = new HashMap<>();

  public void register(String key, EditingPreset preset) {
    prototypes.put(key, preset.copy());
  }

  /** Returns a fresh copy of the preset stored under {@code key}. */
  public EditingPreset get(String key) {
    EditingPreset prototype = prototypes.get(key);
    if (prototype == null) {
      throw new IllegalArgumentException("No preset registered under: " + key);
    }
    return prototype.copy();
  }
}
