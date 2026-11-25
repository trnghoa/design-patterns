/*
 * @(#)InventoryService.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.facade;
import java.util.HashMap;
import java.util.Map;
/**
 * Subsystem: keeps stock per product and reserves units for orders.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class InventoryService {
  private final Map<String, Integer> stock = new HashMap<>();
  public void addStock(String productId, int quantity) {
    stock.merge(productId, quantity, Integer::sum);
  }
  public int getStock(String productId) {
    return stock.getOrDefault(productId, 0);
  }
  /** Takes the units out of stock. Returns {@code false} and changes nothing if too few. */
  public boolean reserve(String productId, int quantity) {
    if (getStock(productId) < quantity) {
      return false;
    }
    stock.merge(productId, -quantity, Integer::sum);
    System.out.println("  [inventory] reserved " + quantity + " x " + productId);
    return true;
  }
  /** Puts reserved units back, for example when payment fails. */
  public void release(String productId, int quantity) {
    stock.merge(productId, quantity, Integer::sum);
    System.out.println("  [inventory] released " + quantity + " x " + productId);
  }
}
