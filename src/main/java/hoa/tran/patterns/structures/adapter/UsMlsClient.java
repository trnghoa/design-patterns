/*
 * @(#)UsMlsClient.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.adapter;
import java.util.List;
/**
 * The <em>adaptee</em>: a third-party client we cannot change. Its method name, parameters and
 * data format all differ from {@link ListingSource}.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class UsMlsClient {
  public List<MlsRecord> searchByCity(String cityName) {
    return List.of(
        new MlsRecord("221 Baker Ave", cityName, 1_200, 45_000_000L),
        new MlsRecord("10 Ocean Dr", cityName, 2_500, 120_000_000L));
  }
}
