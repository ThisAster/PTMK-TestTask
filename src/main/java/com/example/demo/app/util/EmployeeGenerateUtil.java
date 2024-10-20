package com.example.demo.app.util;

import com.example.demo.app.entity.Employee;
import com.example.demo.app.util.properties.EmployeeProperties;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmployeeGenerateUtil {

    private static final Random random = new Random();

    private final EmployeeProperties employeeProperties;

    public Employee generateEmployee() {
        String gender = generateGender();
        String fullName = generateFullName(gender);
        LocalDate birthDate = generateRandomBirthDate();
        return new Employee(null, fullName, birthDate, gender);
    }

    public List<Employee> generateEmployees(int count) {
        List<Employee> employees = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            employees.add(generateEmployee());
        }
        return employees;
    }

    private String generateGender() {
        return random.nextBoolean() ? "Male" : "Female";
    }

    public String generateFullName(String gender) {
        String firstName;
        String lastName;
        String patronymic;

        if ("Male".equals(gender)) {
            firstName = getRandomElement(employeeProperties.getMaleFirstNames());
            lastName = getRandomElement(employeeProperties.getMaleLastNames());
            patronymic = getRandomElement(employeeProperties.getMaleMiddleNames());
        } else {
            firstName = getRandomElement(employeeProperties.getFemaleFirstNames());
            lastName = getRandomElement(employeeProperties.getFemaleLastNames());
            patronymic = getRandomElement(employeeProperties.getFemaleMiddleNames());
        }

        return String.format("%s %s %s", lastName, firstName, patronymic);
    }

    public LocalDate generateRandomBirthDate() {
        int year = 1980 + random.nextInt(23);
        int dayOfYear = 1 + random.nextInt(365);
        return LocalDate.ofYearDay(year, dayOfYear);
    }

    public String generateFullNameWithLastNameStartingWithF() {
        String lastName = getRandomElement(employeeProperties.getLastNamesStartingWithF());
        String firstName = getRandomElement(employeeProperties.getMaleFirstNames());
        return String.format("%s %s %s", lastName, firstName, generateMalePatronymic(firstName));
    }
    public List<Employee> generateEmployeesWithLastNameStartingWithF(int count) {
        List<Employee> employees = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String fullName = generateFullNameWithLastNameStartingWithF();
            LocalDate birthDate = generateRandomBirthDate();
            employees.add(new Employee(null, fullName, birthDate, "Male"));
        }
        return employees;
    }


    private String generateMalePatronymic(String fatherFirstName) {
        return fatherFirstName + "ovich";
    }

    private <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
