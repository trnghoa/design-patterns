/*
 * @(#)AdapterDemo.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
import java.util.List;
/**
 * Runs the Adapter example. The report code only knows {@link ListingSource}, yet it reads from
 * both the Danish provider and, through the adapter, the US partner.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class AdapterDemo {
  static final double EUR_PER_USD = 0.92;
  public static void main(String[] args) {
    printReport("Copenhagen", new DanishListingSource());
    printReport("Boston", new UsMlsListingAdapter(new UsMlsClient(), EUR_PER_USD));
  }
  private static void printReport(String city, ListingSource source) {
    System.out.println(city + ":");
    List<Listing> listings = source.fetchListings(city);
    for (Listing listing : listings) {
      System.out.printf("  %-24s %7.1f m2 %,12.2f EUR%n",
          listing.address(), listing.areaSqm(), listing.priceEur());
    }
  }
}
