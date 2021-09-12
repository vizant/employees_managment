package com.example.employeemanagment.dao;

import com.example.employeemanagment.enitity.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeDao {
    private static EmployeeDao instance;

    private static final String jdbcURL = "jdbc:postgresql://localhost:5432/employees";
    private static final String jdbcUsername = "employee";
    private static final String jdbcPassword = "employee";
    private static final String jdbcDriver = "org.postgresql.Driver";

    private EmployeeDao() {
    }

    public static EmployeeDao getInstance() {
        if (instance == null) {
            instance = new EmployeeDao();
        }
        return instance;
    }


    private Connection getConnection() {
        Properties props = new Properties();
        props.setProperty("user", jdbcUsername);
        props.setProperty("password", jdbcPassword);

        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername,jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void addEmployee(Employee employee) {
        try (Connection connection = getConnection();
             PreparedStatement p = connection.prepareStatement("insert into employees " +
                     "(first_name, last_name, middle_name, date_of_birth, mail) VALUES (?,?,?,?,?);")) {
            p.setString(1, employee.getFirstName());
            p.setString(2, employee.getLastName());
            p.setString(3, employee.getMiddleName());
            p.setDate(4, Date.valueOf(employee.getDateOfBirth()));
            p.setString(5, employee.getMail());
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployee(int id) {
        Employee employee = null;
        try (Connection connection = getConnection();
             PreparedStatement p = connection.prepareStatement("select id, first_name, " +
                     "last_name, middle_name, date_of_birth, mail from employees where id = ?;")) {
            p.setInt(1, id);
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lasName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                LocalDate date = resultSet.getDate("date_of_birth").toLocalDate();
                String mail = resultSet.getString("mail");
                employee = new Employee(id, firstName, lasName, middleName, date, mail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement p = connection.prepareStatement("select * from employees;")) {
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lasName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                LocalDate date = resultSet.getDate("date_of_birth").toLocalDate();
                String mail = resultSet.getString("mail");
                employees.add(new Employee(id, firstName, lasName, middleName, date, mail));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public boolean updateEmployee(Employee employee) {
        boolean updateResult = false;
        try (Connection connection = getConnection();
             PreparedStatement p = connection.prepareStatement("update employees " +
                     "set first_name = ?, last_name = ?, middle_name = ?, date_of_birth = ?, mail = ? where id = ?;")) {
            p.setString(1, employee.getFirstName());
            p.setString(2, employee.getLastName());
            p.setString(3, employee.getMiddleName());
            p.setDate(4, Date.valueOf(employee.getDateOfBirth()));
            p.setString(5, employee.getMail());
            p.setInt(6, employee.getId());
            updateResult = p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateResult;
    }

    public boolean deleteEmployee(int id) {
        boolean deleteResult = false;
        try (Connection connection = getConnection();
             PreparedStatement p = connection.prepareStatement("delete from employees where id = ?;")) {
            p.setInt(1, id);
            deleteResult = p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleteResult;
    }
}
