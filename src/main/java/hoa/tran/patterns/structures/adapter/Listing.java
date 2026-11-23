/*
 * @(#)Listing.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
/**
 * A property listing in the format our application uses: metric area and price in euros.
 *
 * @author hoa.tn
 * @since 1.0
 */
public record Listing(String address, double areaSqm, double priceEur) {
}
