/*
 * @(#)CompositeDemo.java
 *
 * Copyright 2026 by hoa.tn.
 * All rights reserved.
 */
package hoa.tran.patterns.structures.composite;
/**
 * Runs the Composite example. It builds this company tree:
 *
 * <pre>
 * Company
 * ├── CEO
 * ├── Engineering
 * │   ├── Lead developer
 * │   └── Backend team
 * │       ├── Developer A
 * │       └── Developer B
 * └── Sales
 *     └── Sales manager
 * </pre>
 *
 * The caller asks every unit for {@code getExpenses()} in the same way, whether it is a single
 * employee or a whole department with sub-departments.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class CompositeDemo {
  public static void main(String[] args) {
    Employee ceo = new Employee("CEO", 10_000);
    Employee leadDeveloper = new Employee("Lead developer", 6_000);
    Employee developerA = new Employee("Developer A", 4_000);
    Employee developerB = new Employee("Developer B", 3_500);
    Employee salesManager = new Employee("Sales manager", 5_000);

    Department backendTeam = new Department();
    backendTeam.add(developerA);
    backendTeam.add(developerB);

    Department engineering = new Department();
    engineering.add(leadDeveloper);
    engineering.add(backendTeam);

    Department sales = new Department();
    sales.add(salesManager);

    Department company = new Department();
    company.add(ceo);
    company.add(engineering);
    company.add(sales);

    print("Developer A (employee)", developerA);
    print("Backend team", backendTeam);
    print("Engineering", engineering);
    print("Sales", sales);
    print("Company", company);
  }

  private static void print(String label, CompanyUnit unit) {
    System.out.printf("%-24s %,10.2f EUR%n", label, unit.getExpenses());
  }
}
