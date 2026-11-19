/*
 * @(#)CompositeTest.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Checks that employees and departments report their expenses the same way, and that
 * departments add up their children at any depth.
 *
 * @author hoa.tn
 * @since 1.0
 */
class CompositeTest {

  @Test
  void employeeExpensesAreItsSalary() {
    CompanyUnit employee = new Employee("Developer A", 4_000);
    assertEquals(4_000, employee.getExpenses());
  }

  @Test
  void emptyDepartmentHasNoExpenses() {
    assertEquals(0, new Department().getExpenses());
  }

  @Test
  void departmentAddsUpItsEmployees() {
    Department team = new Department();
    team.add(new Employee("Developer A", 4_000));
    team.add(new Employee("Developer B", 3_500));
    assertEquals(7_500, team.getExpenses());
  }

  @Test
  void departmentIncludesNestedDepartments() {
    Department backendTeam = new Department();
    backendTeam.add(new Employee("Developer A", 4_000));
    backendTeam.add(new Employee("Developer B", 3_500));

    Department engineering = new Department();
    engineering.add(new Employee("Lead developer", 6_000));
    engineering.add(backendTeam);

    Department sales = new Department();
    sales.add(new Employee("Sales manager", 5_000));

    Department company = new Department();
    company.add(new Employee("CEO", 10_000));
    company.add(engineering);
    company.add(sales);

    assertEquals(13_500, engineering.getExpenses());
    assertEquals(28_500, company.getExpenses());
  }

  @Test
  void expensesFollowChangesInChildDepartments() {
    Department team = new Department();
    Department company = new Department();
    company.add(team);
    assertEquals(0, company.getExpenses());

    team.add(new Employee("New hire", 3_000));
    assertEquals(3_000, company.getExpenses());
  }
}
