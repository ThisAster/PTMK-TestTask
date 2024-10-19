package com.example.demo.app.employee.util;

import com.github.javafaker.Faker;
import com.example.demo.app.employee.entity.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeGenerateUtil {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private static final String[] lastNamesStartingWithF = {"Fedorov", "Fomin", "Fedotov", "Filippov", "Fursov"};

    public static List<Employee> generateEmployees(int count) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String fullName = faker.name().fullName();
            LocalDate birthDate = generateRandomBirthDate();
            String gender = faker.options().option("Male", "Female");

            employees.add(new Employee(null, fullName, birthDate, gender));
        }

        return employees;
    }

    public static LocalDate generateRandomBirthDate() {
        int year = 1980 + faker.random().nextInt(21);
        int dayOfYear = 1 + faker.random().nextInt(365);
        return LocalDate.ofYearDay(year, dayOfYear);
    }

    public static String generateFullNameWithLastNameStartingWithF() {
        String lastName = lastNamesStartingWithF[random.nextInt(lastNamesStartingWithF.length)];
        String firstName = faker.name().firstName();
        String middleName = faker.name().lastName();
        return String.format("%s %s %s", lastName, firstName, middleName);
    }
}
