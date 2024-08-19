package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersActions {

    // Singleton instance of UsersActions
    private static UsersActions instance;

    // Map to store employees with their ID as the key
    private Map<Integer, Users> employees = new HashMap<>();

    // Variable to store the current logged in employee
    private Users currentEmployee;

    // Counter to keep track of the total number of users
    private Integer userCount = 0;

    // Private constructor to prevent instantiation
    private UsersActions() {
    }

    // Singleton instance getter
    public static synchronized UsersActions getInstance() {
        if (instance == null) {
            instance = new UsersActions();
        }
        return instance;
    }

    // Get the current logged in employee
    public Users getCurrentEmployee() {
        return currentEmployee;
    }

    // Get the total number of users
    public Integer getUserCount() {
        return userCount;
    }

    // Create a new Users object
    private Users createEmployee(Integer employeeID, String employeeUser, String employeePassword,
            String employeeName, String employeeAddress, String employeeEmail, String employeeRole) {
        return new Users(employeeID, employeeUser, employeePassword,
                employeeName, employeeAddress, employeeEmail, employeeRole);
    }

    // Validate and format name: capitalize the first letter, rest in lowercase
    public String nameAdditionalValidation(String value) {
        if (value.isBlank()) {
            return value; // Return as is if null or empty
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // List employees, optionally filtering by name
    public Map<Integer, Users> listEmployees(String employeeName) {
        if (employeeName.isBlank()) {
            return employees; // Return all employees if no name is provided
        } else {
            Map<Integer, Users> filteredEmployees = new HashMap<>();
            for (Users employee : employees.values()) {
                if (employee.getEmployeeName().equalsIgnoreCase(employeeName)) {
                    filteredEmployees.put(employee.getEmployeeID(), employee);
                }
            }
            return filteredEmployees.isEmpty() ? employees : filteredEmployees;
        }
    }

    // Validate email format
    public boolean emailValidation(String employeeEmail) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(employeeEmail);
        return matcher.matches();
    }

    // Check if email is unique
    public boolean isEmailUnique(String employeeEmail) {
        return employees.values().stream()
                .noneMatch(employee -> employee.getEmployeeEmail().equals(employeeEmail));
    }

    // Check if username is unique
    public boolean userValidation(String employeeUser) {
        return employees.values().stream()
                .noneMatch(employee -> employee.getEmployeeUser().equals(employeeUser));
    }

    // Add a new employee
    public void addEmployee(String employeeUser, String employeePassword, String employeeName,
            String employeeAddress, String employeeEmail, String employeeRole) {
        Users employee = createEmployee(userCount, employeeUser, employeePassword,
                employeeName, employeeAddress, employeeEmail, employeeRole);
        employees.put(userCount, employee);
        userCount++;
    }

    // Modify an existing employee
    public boolean modifyEmployee(Integer employeeID, String employeeUser, String employeePassword,
            String employeeName, String employeeAddress, String employeeEmail, String employeeRole) {
        if (employees.containsKey(employeeID) && (userValidation(employeeUser) || employees.get(employeeID).getEmployeeUser().equals(employeeUser))) {
            Users employee = createEmployee(employeeID, employeeUser, employeePassword,
                    employeeName, employeeAddress, employeeEmail, employeeRole);
            employees.replace(employeeID, employee);
            return true;
        }
        return false;
    }

    // Modify an existing employee's password
    public boolean modifyPassword(Integer employeeID, String newPassword) {
        if (employees.containsKey(employeeID)) {
            Users employee = employees.get(employeeID);
            employee.setEmployeePassword(newPassword);
            employees.replace(employeeID, employee);
            return true;
        }
        return false;
    }

    // Delete an existing employee
    public boolean deleteEmployee(Integer employeeID) {
        return employees.remove(employeeID) != null;
    }

    // Users login
    public boolean employeeLogin(String employeeUser, String employeePassword) {
        for (Users employee : employees.values()) {
            if (employee.getEmployeeUser().equals(employeeUser) && employee.getEmployeePassword().equals(employeePassword)) {
                currentEmployee = employee; // Set the current employee if login is successful
                return true;
            }
        }
        return false;
    }
}
