package com.example.demo.app.repository;

import com.example.demo.app.entity.Employee;
import com.example.demo.database.DatabaseConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final DatabaseConnection databaseConnection;

    public void createTable() {
        String sql = "CREATE TABLE employees (" +
                    "id SERIAL PRIMARY KEY, " +
                    "full_name VARCHAR(100) NOT NULL, " +
                    "birth_date DATE NOT NULL, " +
                    "gender VARCHAR(10) NOT NULL " +
                    ")";

        String idxForFullName = "CREATE INDEX idx_full_name ON employees USING btree (full_name)";
        String idxForGender = "CREATE INDEX idx_gender ON employees USING btree (gender)";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            statement.execute(idxForFullName);
            statement.execute(idxForGender);

        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Employees table is already created.");
            } else {
                log.info(e.getMessage());
            }
        }
    }

    public void save(Employee employee) {
        String sql = "INSERT INTO employees (full_name, birth_date, gender) VALUES (?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFullName());
            statement.setDate(2, Date.valueOf(employee.getDateOfBirth()));
            statement.setString(3, employee.getGender());

            statement.executeUpdate();

        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees ORDER BY full_name";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String fullName = resultSet.getString("full_name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String gender = resultSet.getString("gender");
                employees.add(new Employee(id, fullName, birthDate, gender));
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return employees;
    }

    public List<Employee> findByGenderAndLastNameStartingWith(String gender, String prefix) {
        String sql = "SELECT * FROM employees WHERE gender = ? AND full_name LIKE ? ORDER BY full_name";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, gender);
            preparedStatement.setString(2, prefix + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String fullName = resultSet.getString("full_name");
                    LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                    employees.add(new Employee(id, fullName, birthDate, gender));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    public void clearAllDataOfTable() {
        String sql = "DELETE FROM employees";
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    public void saveEmployeesBatch(List<Employee> employees) {
        String sql = "INSERT INTO employees (full_name, birth_date, gender) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int count = 0;
            for (Employee employee : employees) {
                statement.setString(1, employee.getFullName());
                statement.setDate(2, Date.valueOf(employee.getDateOfBirth()));
                statement.setString(3, employee.getGender());
                statement.addBatch();

                count++;

                if (count % 1000 == 0) {
                    statement.executeBatch();
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error while saving employees: " + e.getMessage());
        }
    }
}
