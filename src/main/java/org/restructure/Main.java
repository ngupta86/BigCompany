package org.restructure;

import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        EmployeeOperations employeeOperations = new EmployeeOperations();
        // Read csv file and fetch employee details
        String csvFile = "./src/main/resources/EmployeeDetails.csv";
        List<Employee> employees = employeeOperations.readCSVAndGenerateList(csvFile);

        //managers that earn less than they should
        employeeOperations.printManagersEarningLessThanAllowed(employees);

        //managers that earn more than they should
        employeeOperations.printManagersEarningMoreThanAllowed(employees);

        //employees having reporting line of more than 4
        employeeOperations.printEmployeesWithLongerReportingLine(employees);

    }
}