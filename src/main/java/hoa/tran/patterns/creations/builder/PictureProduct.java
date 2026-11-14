/*
 * @(#)PictureProduct.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.builder;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Edited property photos with optional AI services. Instances are immutable and can only
 * be created through {@link Builder}, which is the point of this example: the caller picks
 * the parts step by step, and {@link Builder#build()} checks them before the object exists.
 *
 * <pre>{@code
 * Product photos = PictureProduct.builder()
 *     .photoCount(25)
 *     .blueSky()
 *     .windowDetails()
 *     .build();
 * }</pre>
 *
 * @author hoa.tn
 * @since 1.0
 */
@Getter
public class PictureProduct implements Product {

  static final int INCLUDED_PHOTOS = 10;
  static final int MAX_PHOTOS = 100;
  static final double BASE_PRICE = 15.0;
  static final double EXTRA_PHOTO_PRICE = 1.0;
  static final double BLUE_SKY_PRICE = 5.0;
  static final double WINDOW_DETAILS_PRICE = 5.0;
  static final double STRAIGHTENING_PRICE = 3.0;

  private final String name = "Property photos";
  private final int photoCount;
  private final boolean blueSky;
  private final boolean windowDetails;
  private final boolean straightening;
  private final double price;

  /** Only the builder can call this, so every instance has passed validation. */
  private PictureProduct(Builder builder) {
    this.photoCount = builder.photoCount;
    this.blueSky = builder.blueSky;
    this.windowDetails = builder.windowDetails;
    this.straightening = builder.straightening;
    this.price = calculatePrice();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String produce(String address) {
    return "Shot, edited and colour-corrected %d photos%s of %s"
        .formatted(photoCount, describeExtras(), address);
  }

  private double calculatePrice() {
    double total = BASE_PRICE + Math.max(0, photoCount - INCLUDED_PHOTOS) * EXTRA_PHOTO_PRICE;
    if (blueSky) {
      total += BLUE_SKY_PRICE;
    }
    if (windowDetails) {
      total += WINDOW_DETAILS_PRICE;
    }
    if (straightening) {
      total += STRAIGHTENING_PRICE;
    }
    return total;
  }

  private String describeExtras() {
    List<String> extras = new ArrayList<>();
    if (blueSky) {
      extras.add("blue sky");
    }
    if (windowDetails) {
      extras.add("window details");
    }
    if (straightening) {
      extras.add("straightening");
    }
    return extras.isEmpty() ? "" : " (" + String.join(", ", extras) + ")";
  }

  /**
   * Collects the parts of a {@link PictureProduct}. Each step returns the builder so calls
   * can be chained; nothing is created until {@link #build()}.
   *
   * @author hoa.tn
   * @since 1.0
   */
  public static class Builder {

    private int photoCount = INCLUDED_PHOTOS;
    private boolean blueSky;
    private boolean windowDetails;
    private boolean straightening;

    private Builder() {
    }

    public Builder photoCount(int photoCount) {
      this.photoCount = photoCount;
      return this;
    }

    public Builder blueSky() {
      this.blueSky = true;
      return this;
    }

    public Builder windowDetails() {
      this.windowDetails = true;
      return this;
    }

    public Builder straightening() {
      this.straightening = true;
      return this;
    }

    /** Checks the collected parts, then creates the immutable product. */
    public PictureProduct build() {
      if (photoCount < 1 || photoCount > MAX_PHOTOS) {
        throw new IllegalStateException(
            "photoCount must be between 1 and " + MAX_PHOTOS + ", was " + photoCount);
      }
      return new PictureProduct(this);
    }
  }
}
