/*
 * @(#)FloorplanProduct.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.builder;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Floor plan of a property, 2D or 3D, with optional extras. Instances are immutable and can
 * only be created through {@link Builder}.
 *
 * <pre>{@code
 * Product plan = FloorplanProduct.builder()
 *     .floors(2)
 *     .threeD()
 *     .furniture()
 *     .build();
 * }</pre>
 *
 * @author hoa.tn
 * @since 1.0
 */
@Getter
public class FloorplanProduct implements Product {

  static final int MAX_FLOORS = 10;
  static final double BASE_PRICE = 40.0;
  static final double EXTRA_FLOOR_PRICE = 20.0;
  static final double THREE_D_PRICE = 30.0;
  static final double FURNITURE_PRICE = 10.0;
  static final double MEASUREMENTS_PRICE = 5.0;

  private final String name;
  private final int floors;
  private final boolean threeD;
  private final boolean furniture;
  private final boolean measurements;
  private final double price;

  /** Only the builder can call this, so every instance has passed validation. */
  private FloorplanProduct(Builder builder) {
    this.floors = builder.floors;
    this.threeD = builder.threeD;
    this.furniture = builder.furniture;
    this.measurements = builder.measurements;
    this.name = threeD ? "3D floor plan" : "2D floor plan";
    this.price = calculatePrice();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String produce(String address) {
    return "Measured and drew a %s%s of %s".formatted(name, describeExtras(), address);
  }

  private double calculatePrice() {
    double total = BASE_PRICE + (floors - 1) * EXTRA_FLOOR_PRICE;
    if (threeD) {
      total += THREE_D_PRICE;
    }
    if (furniture) {
      total += FURNITURE_PRICE;
    }
    if (measurements) {
      total += MEASUREMENTS_PRICE;
    }
    return total;
  }

  private String describeExtras() {
    List<String> extras = new ArrayList<>();
    if (floors > 1) {
      extras.add(floors + " floors");
    }
    if (furniture) {
      extras.add("furniture");
    }
    if (measurements) {
      extras.add("measurements");
    }
    return extras.isEmpty() ? "" : " (" + String.join(", ", extras) + ")";
  }

  /**
   * Collects the parts of a {@link FloorplanProduct}. Each step returns the builder so calls
   * can be chained; nothing is created until {@link #build()}.
   *
   * @author hoa.tn
   * @since 1.0
   */
  public static class Builder {

    private int floors = 1;
    private boolean threeD;
    private boolean furniture;
    private boolean measurements;

    private Builder() {
    }

    public Builder floors(int floors) {
      this.floors = floors;
      return this;
    }

    public Builder threeD() {
      this.threeD = true;
      return this;
    }

    public Builder furniture() {
      this.furniture = true;
      return this;
    }

    public Builder measurements() {
      this.measurements = true;
      return this;
    }

    /** Checks the collected parts, then creates the immutable product. */
    public FloorplanProduct build() {
      if (floors < 1 || floors > MAX_FLOORS) {
        throw new IllegalStateException(
            "floors must be between 1 and " + MAX_FLOORS + ", was " + floors);
      }
      return new FloorplanProduct(this);
    }
  }
}
