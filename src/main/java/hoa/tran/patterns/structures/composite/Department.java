/*
 * @(#)Department.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.composite;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoa.tn
 * @since 1.0
 */
public class Department implements CompanyUnit {
  List<CompanyUnit>  companyUnits = new ArrayList<>();
  /** Adds an employee or a sub-department. Both are {@link CompanyUnit}s. */
  public void add(CompanyUnit companyUnit) {
    companyUnits.add(companyUnit);
  }
  @Override
  public double getExpenses() {
    double totalExpenses = 0;
    for (CompanyUnit companyUnit : companyUnits) {
      totalExpenses += companyUnit.getExpenses();
    }
    return totalExpenses;
  }
}
