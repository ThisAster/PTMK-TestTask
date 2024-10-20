package com.example.demo.app.service;

import com.example.demo.app.entity.Employee;
import com.example.demo.app.repository.EmployeeRepository;
import com.example.demo.app.util.EmployeeGenerateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeGenerateUtil employeeGenerateUtil;

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
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            int age = calculateAge(employee.getDateOfBirth());
            String employeeDetail = String.format("Full Name: %s, Birth Date: %s, Gender: %s, Age: %d",
                    employee.getFullName(), employee.getDateOfBirth(), employee.getGender(), age);
            System.out.println(employeeDetail);
        }
    }

    public List<Employee> getByGenderAndLastNameStartingWith(String gender, String prefix) {
        return employeeRepository.findByGenderAndLastNameStartingWith(gender, prefix);
    }

    public void fillDatabaseAutomatically() {
        employeeRepository.saveEmployeesBatch(employeeGenerateUtil.generateEmployees(1_000_000));
        employeeRepository.saveEmployeesBatch(employeeGenerateUtil.generateEmployeesWithLastNameStartingWithF(100));
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
