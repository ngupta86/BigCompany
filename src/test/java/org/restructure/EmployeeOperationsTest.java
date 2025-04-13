package org.restructure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeOperationsTest {

    EmployeeOperations operations = new EmployeeOperations();

    @Test
    public void testCSVReaderWorksForNoLocation() {
        EmployeeOperations ops = new EmployeeOperations();
        List<Employee> employees = ops.readCSVAndGenerateList("");
        assertTrue(employees.isEmpty());
    }

    @Test
    public void testCSVReaderWorksForWrongLocation() {
        EmployeeOperations ops = new EmployeeOperations();
        List<Employee> employees = ops.readCSVAndGenerateList("main/resources/EmployeeDetails.csv");
        assertTrue(employees.isEmpty());
        assertNotNull(employees);
    }

    @Test
    public void testCSVReaderWorksForCorrectLocation() {
        EmployeeOperations ops = new EmployeeOperations();
        List<Employee> employees = ops.readCSVAndGenerateList("./src/test/resources/EmployeeDetails.csv");
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testCSVReaderWorksForMissingData() {
        EmployeeOperations ops = new EmployeeOperations();
        List<Employee> employees = ops.readCSVAndGenerateList("./src/test/resources/EmployeeDetails.csv");
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testManagerEarningLessThanAllowed() {
        //ideal to generate mock employee using @BeforeEach annotation on a method that needs to run before every test
        List<Employee> employees = operations.readCSVAndGenerateList("./src/test/resources/EmployeeDetails.csv");
        operations.printManagersEarningLessThanAllowed(employees);
    }

    @Test
    public void testManagerEarningMoreThanAllowed() {
        //ideal to generate mock employee using @BeforeEach annotation on a method that needs to run before every test
        List<Employee> employees = operations.readCSVAndGenerateList("./src/test/resources/EmployeeDetails.csv");
        operations.printManagersEarningMoreThanAllowed(employees);
    }

    @Test
    public void testReportingLineDepth() {
        //ideal to generate mock employee using @BeforeEach annotation on a method that needs to run before every test
        List<Employee> employees = operations.readCSVAndGenerateList("./src/test/resources/EmployeeDetails.csv");
        operations.printEmployeesWithLongerReportingLine(employees);
    }

}
