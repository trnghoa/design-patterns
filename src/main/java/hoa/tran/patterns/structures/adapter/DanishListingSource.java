/*
 * @(#)DanishListingSource.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
import java.util.List;
/**
 * A provider that already speaks our format, so it implements {@link ListingSource} directly
 * and needs no adapter.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class DanishListingSource implements ListingSource {
  @Override
  public List<Listing> fetchListings(String city) {
    return List.of(
        new Listing("Nyhavn 12, " + city, 85.0, 450_000),
        new Listing("Strøget 7, " + city, 120.0, 690_000));
  }
}
