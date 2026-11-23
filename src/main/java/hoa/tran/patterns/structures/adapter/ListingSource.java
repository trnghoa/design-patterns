/*
 * @(#)ListingSource.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
import java.util.List;
/**
 * The <em>target</em> interface: what our application expects from any listing provider.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface ListingSource {
  List<Listing> fetchListings(String city);
}
