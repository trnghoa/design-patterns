/*
 * @(#)AdapterTest.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Checks that the adapter turns the US partner's records into our {@link Listing} format.
 *
 * @author hoa.tn
 * @since 1.0
 */
class AdapterTest {

  private static final double DELTA = 0.01;

  @Test
  void adapterReturnsOneListingPerRecord() {
    ListingSource source = new UsMlsListingAdapter(new UsMlsClient(), 1.0);
    assertEquals(2, source.fetchListings("Boston").size());
  }

  @Test
  void adapterJoinsStreetAndCityIntoAddress() {
    Listing first = new UsMlsListingAdapter(new UsMlsClient(), 1.0).fetchListings("Boston").get(0);
    assertEquals("221 Baker Ave, Boston", first.address());
  }

  @Test
  void adapterConvertsSquareFeetToSquareMetres() {
    Listing first = new UsMlsListingAdapter(new UsMlsClient(), 1.0).fetchListings("Boston").get(0);
    assertEquals(111.48, first.areaSqm(), DELTA);
  }

  @Test
  void adapterConvertsUsCentsToEuros() {
    Listing first = new UsMlsListingAdapter(new UsMlsClient(), 0.92).fetchListings("Boston").get(0);
    assertEquals(414_000.00, first.priceEur(), DELTA);
  }

  @Test
  void adapterAndNativeSourceAreUsedTheSameWay() {
    List<ListingSource> sources = List.of(
        new DanishListingSource(),
        new UsMlsListingAdapter(new UsMlsClient(), 0.92));
    for (ListingSource source : sources) {
      assertEquals(2, source.fetchListings("Anywhere").size());
    }
  }
}
