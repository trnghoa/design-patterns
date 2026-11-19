/*
 * @(#)Employee.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.composite;

/**
 *
 * @author hoa.tn
 * @since 1.0
 */
public class Employee implements CompanyUnit {

  private String name;
  private double salary;
  public Employee(String name, double salary) {
    this.name = name;
    this.salary = salary;
  }
  @Override
  public double getExpenses() {
    return salary;
  }
}
