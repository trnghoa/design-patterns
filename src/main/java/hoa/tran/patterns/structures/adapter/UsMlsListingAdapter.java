/*
 * @(#)UsMlsListingAdapter.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
import java.util.List;
/**
 * The <em>adapter</em>. It implements the interface our application expects and translates each
 * call to the {@link UsMlsClient}, converting the results into our {@link Listing} format.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class UsMlsListingAdapter implements ListingSource {
  static final double SQM_PER_SQFT = 0.09290304;
  private final UsMlsClient client;
  private final double eurPerUsd;
  public UsMlsListingAdapter(UsMlsClient client, double eurPerUsd) {
    this.client = client;
    this.eurPerUsd = eurPerUsd;
  }
  @Override
  public List<Listing> fetchListings(String city) {
    return client.searchByCity(city).stream()
        .map(this::toListing)
        .toList();
  }
  private Listing toListing(MlsRecord mlsRecord) {
    String address = mlsRecord.streetLine() + ", " + mlsRecord.city();
    double areaSqm = mlsRecord.squareFeet() * SQM_PER_SQFT;
    double priceEur = mlsRecord.priceUsdCents() / 100.0 * eurPerUsd;
    return new Listing(address, areaSqm, priceEur);
  }
}
