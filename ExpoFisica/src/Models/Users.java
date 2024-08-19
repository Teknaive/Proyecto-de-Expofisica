package Models;

public class Users {

    // Unique identifier for the employee
    private Integer employeeID;
    // Username of the employee
    private String employeeUser;
    // Password of the employee
    private String employeePassword;
    // Name of the employee
    private String employeeName;
    // Address of the employee
    private String employeeAddress;
    // Email of the employee
    private String employeeEmail;
    // Role of the employee
    private String employeeRol;

    // Default constructor
    public Users() {
    }

    // Parameterized constructor
    public Users(Integer employeeID, String employeeUser, String employeePassword, String employeeName, String employeeAddress, String employeeEmail, String employeeRol) {
        this.employeeID = employeeID;
        this.employeeUser = employeeUser;
        this.employeePassword = employeePassword;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeEmail = employeeEmail;
        this.employeeRol = employeeRol;
    }

    // Get the employee's ID
    public Integer getEmployeeID() {
        return employeeID;
    }

    // Set the employee's ID
    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    // Get the employee's username
    public String getEmployeeUser() {
        return employeeUser;
    }

    // Set the employee's username
    public void setEmployeeUser(String employeeUser) {
        this.employeeUser = employeeUser;
    }

    // Get the employee's password
    public String getEmployeePassword() {
        return employeePassword;
    }

    // Set the employee's password
    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    // Get the employee's name
    public String getEmployeeName() {
        return employeeName;
    }

    // Set the employee's name
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    // Get the employee's address
    public String getEmployeeAddress() {
        return employeeAddress;
    }

    // Set the employee's address
    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    // Get the employee's email
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    // Set the employee's email
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    // Get the employee's role
    public String getEmployeeRol() {
        return employeeRol;
    }

    // Set the employee's role
    public void setEmployeeRol(String employeeRol) {
        this.employeeRol = employeeRol;
    }

}
