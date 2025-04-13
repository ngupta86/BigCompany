package org.restructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeOperations {

    public List<Employee> readCSVAndGenerateList(String csvFile) {
        String row;
        String splitBy = ",";

        List<Employee> employees = new ArrayList<Employee>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            boolean isHeader = true;

            while ((row = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                Employee emp = getEmployee(row, splitBy);

                employees.add(emp);
            }
        } catch (IOException e) {
            System.out.println("Unable to read the file and process it. Error: " + e);
        }
        return employees;
    }

    private static Employee getEmployee(String row, String splitBy) {
        String[] data = row.split(splitBy);
        //Create new Employee object and add to List
        Employee emp = new Employee();
        emp.setId(Integer.parseInt(data[0]));
        emp.setFirstName(data[1]);
        emp.setLastName(data[2]);
        emp.setSalary(data[3] != null ? Integer.parseInt(data[3]) : 0);
        if (data.length == 5) {
            emp.setManagerId(data[4] != null ? Integer.parseInt(data[4]) : 0);
        }
        return emp;
    }

    private Map<Long, Employee> getIdToEmpDetails(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));
    }

    private Map<Long, Double> getManagerToAvgSalary(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getManagerId,
                        Collectors.averagingDouble(Employee::getSalary)
                ));
    }

    public void printManagersEarningLessThanAllowed(List<Employee> employees) {
        //EmployeeId to Employee
        Map<Long, Employee> employeeMap = getIdToEmpDetails(employees);

        //ManagerID to Average salary
        Map<Long, Double> managerToAvgSalary = getManagerToAvgSalary(employees);

        getManagersEarningLess(managerToAvgSalary, employeeMap);
    }

    private void getManagersEarningLess(Map<Long, Double> managerToAvgSalary, Map<Long, Employee> employeeMap) {
        Map<Employee, Double> managersEarningLess = new HashMap<>();
        // Compare salary of manager to average of subordinates
        managerToAvgSalary.forEach((id, avgSal) -> {
            if (id != 0) {
                Employee manager = employeeMap.get(id);
                long managerSal = manager.getSalary();
                double minRequired = avgSal * 1.2;
                if (managerSal < minRequired) {
                    managersEarningLess.put(manager, (minRequired - managerSal));
                }
            }
        });
        System.out.println("Managers earning Less:");
        managersEarningLess.forEach((employee, sal) -> System.out.println("Manager " + employee.getFirstName() + " "
                + employee.getLastName() + " gets " + sal.toString() + " less salary than his subordinates"));
    }

    public void printManagersEarningMoreThanAllowed(List<Employee> employees) {
        //EmployeeId to Employee
        Map<Long, Employee> employeeMap = getIdToEmpDetails(employees);

        //ManagerID to Average salary
        Map<Long, Double> managerToAvgSalary = getManagerToAvgSalary(employees);
        getManagersEarningMore(managerToAvgSalary, employeeMap);
    }

    private void getManagersEarningMore(Map<Long, Double> managerToAvgSalary, Map<Long, Employee> employeeMap) {
        Map<Employee, Double> managersEarningMore = new HashMap<>();
        // Compare salary of manager to average of subordinates
        managerToAvgSalary.forEach((id, avgSal) -> {
            if (id != 0) {
                Employee manager = employeeMap.get(id);
                long managerSal = manager.getSalary();
                double maxAllowed = avgSal * 1.5;
                if (managerSal > maxAllowed) {
                    managersEarningMore.put(manager, (managerSal - maxAllowed));
                }
            }
        });
        System.out.println("Managers earning More:");
        managersEarningMore.forEach((employee, sal) -> System.out.println("Manager " + employee.getFirstName() + " "
                + employee.getLastName() + " gets " + sal.toString() + " more salary than his subordinates"));
    }

    public void printEmployeesWithLongerReportingLine(List<Employee> employees) {
        Map<Long, Employee> employeeMap = getIdToEmpDetails(employees);
        Map<Long, Integer> empReportDepthMap = new HashMap<>();
        // Loop through employee map and find the reporting depth
        for (Long empId : employeeMap.keySet()) {
            Employee employee = employeeMap.get(empId);
            if (employee.getManagerId() == 0) {
                continue;
            }
            getCountOfReportingLine(employeeMap, employee, empReportDepthMap);
        }
        System.out.println("Employees with longest chain of reporting are: ");
        // Print employees with depth more than 4
        for (Map.Entry<Long, Integer> entry : empReportDepthMap.entrySet()) {
            if (entry.getValue() > 4) {
                Employee employee = employeeMap.get(entry.getKey());
                System.out.println("Employee " + employee.getFirstName() + employee.getLastName()
                        + " has a reporting depth of " + entry.getValue() + " which is greater than 4.");
            }
        }
    }

    private int getCountOfReportingLine(Map<Long, Employee> employeeMap, Employee employee, Map<Long, Integer> empReportDepthMap) {
        Employee manager = employeeMap.get(employee.getManagerId());
        if (manager == null) {
            return 1;
        }

        int count = 1 + getCountOfReportingLine(employeeMap, manager, empReportDepthMap);
        empReportDepthMap.put(employee.getId(), count);
        return count;
    }

}


