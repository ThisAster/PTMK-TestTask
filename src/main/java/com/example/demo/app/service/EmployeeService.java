package com.example.demo.app.service;

import com.example.demo.app.entity.Employee;
import com.example.demo.app.repository.EmployeeRepository;
import com.example.demo.app.util.EmployeeGenerateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public void initTable() {
        employeeRepository.createTable();
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void clearAllEmployees() {
        employeeRepository.clearAllDataOfTable();
    }

    public void getUniqueAllEmployeesByFullNameAndBithDate() {
        Set<String> uniqueEmployees = new HashSet<>();

        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            int age = calculateAge(employee.getDateOfBirth());
            String employeeDetail = String.format("Full Name: %s, Birth Date: %s, Gender: %s, Age: %d",
                    employee.getFullName(), employee.getDateOfBirth(), employee.getGender(), age);
            uniqueEmployees.add(employeeDetail);
        }

        uniqueEmployees.stream()
                .sorted()
                .forEach(System.out::println);
    }

    public List<Employee> getByGenderAndLastNameStartingWith(String gender, String prefix) {
        return employeeRepository.findByGenderAndLastNameStartingWith(gender, prefix);
    }

    public void fillDatabaseAutomatically() {
        List<Employee> employees = EmployeeGenerateUtil.generateEmployees(1_000_000);
        employeeRepository.saveEmployeesBatch(employees);

        List<Employee> employeesWithStartF = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            String fullName = EmployeeGenerateUtil.generateFullNameWithLastNameStartingWithF();
            LocalDate birthDate = EmployeeGenerateUtil.generateRandomBirthDate();
            employeesWithStartF.add(new Employee(null, fullName, birthDate, "Male"));
        }

        employeeRepository.saveEmployeesBatch(employeesWithStartF);
    }
    public void searchByGenderAndLastNameStartingWith(String gender, String prefix) {
        var employees = getByGenderAndLastNameStartingWith(gender, prefix);

        employees.forEach(employee -> {
            int age = calculateAge(employee.getDateOfBirth());
            System.out.printf("%s, Birth Date: %s, Gender: %s, Age: %d%n",
                    employee.getFullName(), employee.getDateOfBirth(), employee.getGender(), age);
        });
    }

    private int calculateAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}
