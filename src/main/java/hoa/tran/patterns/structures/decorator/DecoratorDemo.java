/*
 * @(#)DecoratorDemo.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.decorator;
/**
 * Runs the Decorator example. The discount is added by wrapping the order, without changing
 * {@link ProductOrder}.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class DecoratorDemo {
  public static void main(String[] args) {
    System.out.println("Plain order:");
    System.out.println("  " + new ProductOrder(5).describe());
    System.out.println("With volume discount:");
    for (int quantity : new int[] {3, 4, 5, 10}) {
      Order order = new VolumeDiscountDecorator(new ProductOrder(quantity));
      System.out.println("  " + order.describe());
    }
  }
}
