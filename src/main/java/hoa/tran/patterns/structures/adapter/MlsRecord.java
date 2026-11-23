/*
 * @(#)MlsRecord.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
/**
 * A listing in the US partner's format: address split in two, area in square feet, price in
 * US cents.
 *
 * @author hoa.tn
 * @since 1.0
 */
public record MlsRecord(String streetLine, String city, int squareFeet, long priceUsdCents) {
}
