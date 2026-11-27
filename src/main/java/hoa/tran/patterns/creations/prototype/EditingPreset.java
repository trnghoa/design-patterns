/*
 * @(#)EditingPreset.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.prototype;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * A photo editing preset: a colour style plus the list of AI services to run. Setting one up
 * takes many steps, so new presets are made by copying a ready one and changing a few values.
 *
 * <p>The copy constructor creates a new {@code services} list. Without that, the copy and
 * the original would share one list, and adding a service to the copy would also add it
 * to the original (a "shallow copy" bug).
 *
 * @author hoa.tn
 * @since 1.0
 */
@Getter
public class EditingPreset implements Prototype<EditingPreset> {

  private String name;
  private String colourStyle;
  private final List<String> services;

  public EditingPreset(String name, String colourStyle, List<String> services) {
    this.name = name;
    this.colourStyle = colourStyle;
    this.services = new ArrayList<>(services);
  }

  /** Copy constructor used by {@link #copy()}. */
  private EditingPreset(EditingPreset source) {
    this.name = source.name;
    this.colourStyle = source.colourStyle;
    this.services = new ArrayList<>(source.services);
  }

  @Override
  public EditingPreset copy() {
    return new EditingPreset(this);
  }

  public EditingPreset rename(String name) {
    this.name = name;
    return this;
  }

  public EditingPreset colourStyle(String colourStyle) {
    this.colourStyle = colourStyle;
    return this;
  }

  public EditingPreset addService(String service) {
    services.add(service);
    return this;
  }

  public EditingPreset removeService(String service) {
    services.remove(service);
    return this;
  }

  /** Read-only view, so callers cannot change the list behind the preset's back. */
  public List<String> getServices() {
    return List.copyOf(services);
  }

  @Override
  public String toString() {
    return "%s [style=%s, services=%s]".formatted(name, colourStyle, services);
  }
}
