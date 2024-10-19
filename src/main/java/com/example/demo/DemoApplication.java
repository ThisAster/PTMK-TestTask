package com.example.demo;

import com.example.demo.app.employee.entity.Employee;
import com.example.demo.app.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

	private final EmployeeService employeeService;

	@Override
	public void run(String... args) {
		if (args.length == 0) {
			System.out.println("No arguments provided.");
			return;
		}

		switch (args[0]) {
			case "1":
				employeeService.initTable();
				break;
			case "2":
				if (args.length < 4) {
					System.out.println("Please write full name, birth date, and gender.");
					return;
				}
				Employee employee = new Employee(null, args[1], LocalDate.parse(args[2]), args[3]);
				employeeService.addEmployee(employee);
				System.out.println("Employee added: " + employee.getFullName());
				break;
			case "3":
				employeeService.getUniqueAllEmployeesByFullNameAndBithDate();
				break;
			case "4":
				employeeService.fillDatabaseAutomatically();
				System.out.println("Database filled with 1_000_000 employee records.");
				break;
			case "5":
				if (args.length < 3) {
					System.out.println("Please provide gender and last name prefix.");
					return;
				}
				employeeService.searchByGenderAndLastNameStartingWith(args[1], args[2]);
				break;
			case "6":
				employeeService.clearAllEmployees();
				System.out.println("All employee records have been cleared.");
				break;
			default:
				System.out.println("Unknown operation mode.");
				break;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
