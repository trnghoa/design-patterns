/*
 * @(#)SingletonTest.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

/**
 * Checks that there is only one {@link PriceList} and that it returns the right prices.
 *
 * @author hoa.tn
 * @since 1.0
 */
class SingletonTest {

  @Test
  void everyCallReturnsTheSameInstance() {
    assertSame(PriceList.getInstance(), PriceList.getInstance());
  }

  @Test
  void manyThreadsSeeOneInstance() {
    Set<PriceList> seen = ConcurrentHashMap.newKeySet();
    try (ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 1_000; i++) {
        pool.submit(() -> seen.add(PriceList.getInstance()));
      }
    }
    assertEquals(1, seen.size());
  }

  @Test
  void returnsKnownPrices() {
    PriceList priceList = PriceList.getInstance();
    assertEquals(15.0, priceList.priceOf("Property photos"));
    assertEquals(40.0, priceList.priceOf("2D floor plan"));
  }

  @Test
  void rejectsUnknownProduct() {
    assertThrows(IllegalArgumentException.class,
        () -> PriceList.getInstance().priceOf("Drone footage"));
  }
}
