/*
 * @(#)SingletonDemo.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.singleton;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Runs the Singleton example. Two unrelated parts of the application ask for the price list
 * and get the same object. Then many threads ask at once, and still only one object exists.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class SingletonDemo {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Before the first getInstance() call");

    PriceList forCheckout = PriceList.getInstance();
    PriceList forInvoice = PriceList.getInstance();

    System.out.printf("Checkout: Property photos = %.2f EUR%n",
        forCheckout.priceOf("Property photos"));
    System.out.printf("Invoice:  Property photos = %.2f EUR%n",
        forInvoice.priceOf("Property photos"));
    System.out.println("Same object? " + (forCheckout == forInvoice));

    Set<PriceList> seenByThreads = ConcurrentHashMap.newKeySet();
    try (ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 1_000; i++) {
        pool.submit(() -> seenByThreads.add(PriceList.getInstance()));
      }
    }
    System.out.println("Distinct instances seen by 1000 threads: " + seenByThreads.size());
  }
}
