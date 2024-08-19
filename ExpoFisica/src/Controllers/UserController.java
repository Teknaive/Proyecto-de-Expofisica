package Controllers;

import Models.Users;
import Models.UsersActions;
import Views.Administration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration; // Reference to the administration view
    private final UsersActions employeeActions; // Handles employee-related actions
    private DefaultTableModel model; // Table model for the employees table

    public UserController(Administration administration, UsersActions employeeActions) {
        this.administration = administration; // Initialize the administration view
        this.employeeActions = employeeActions; // Initialize the employee actions handler
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
        // Add key listeners to search field
        addKeyListeners();
    }

    private void addActionListeners() {
        // Attach action listeners to employee-related buttons
        administration.btnEmployeeRegister.addActionListener(this);
        administration.btnEmployeeUpdate.addActionListener(this);
        administration.btnEmployeeDelete.addActionListener(this);
        administration.btnEmployeeCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        // Attach mouse listeners to the employees table and label
        administration.lblEmployees.addMouseListener(this);
        administration.employeesTable.addMouseListener(this);
    }

    private void addKeyListeners() {
        // Attach key listeners to the search employee input field
        administration.txtSearchEmployee.addKeyListener(this);
    }

    private void modifyEmployee() {
        // Modify an existing employee and refresh data
        try {
            String currentEmail = administration.employeesTable.getValueAt(administration.employeesTable.getSelectedRow(), 4).toString();
            String currentUser = administration.employeesTable.getValueAt(administration.employeesTable.getSelectedRow(), 2).toString();
            // Validate email format
            if (!employeeActions.emailValidation(administration.txtEmployeeEmail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } // Check if email is unique
            else if (!employeeActions.isEmailUnique(administration.txtEmployeeEmail.getText().trim().toLowerCase())
                    && !administration.txtEmployeeEmail.getText().trim().equalsIgnoreCase(currentEmail)) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } // Validate username
            else if (!employeeActions.userValidation(administration.txtEmployeeUser.getText().trim())
                    && !administration.txtEmployeeUser.getText().trim().equalsIgnoreCase(currentUser)) {
                JOptionPane.showMessageDialog(null, "Username already in use.");
            } // Proceed with modifying the employee
            else if (employeeActions.modifyEmployee(Integer.parseInt(administration.txtEmployeeID.getText()),
                    administration.txtEmployeeUser.getText().trim(),
                    administration.txtEmployeePassword.getText(),
                    employeeActions.nameAdditionalValidation(administration.txtEmployeeName.getText()),
                    administration.txtEmployeeAddress.getText().trim(),
                    administration.txtEmployeeEmail.getText().trim().toLowerCase(),
                    administration.cmbEmployeeRol.getSelectedItem().toString())) {
                refreshEmployeeData(); // Refresh the employee data in the view
                JOptionPane.showMessageDialog(null, "Employee successfully modified."); // Success message
                administration.btnEmployeeRegister.setEnabled(true); // Enable register button
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle exceptions
        }
    }

    private void deleteEmployee() {
        // Delete an employee and refresh data
        try {
            // Attempt to delete the employee
            if (employeeActions.deleteEmployee(Integer.valueOf(administration.txtEmployeeID.getText()))) {
                refreshEmployeeData(); // Refresh employee data in the view
                JOptionPane.showMessageDialog(null, "Employee successfully removed from the staff."); // Success message
                administration.btnEmployeeRegister.setEnabled(true); // Enable register button
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle exceptions
        }
    }

    private void cleanTable() {
        // Clear the employee table
        model = (DefaultTableModel) administration.employeesTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove all rows from the table
        }
    }

    private void cleanFields() {
        // Clear employee input fields
        administration.txtEmployeeAddress.setText(""); // Clear address field
        administration.txtEmployeeEmail.setText(""); // Clear email field
        administration.txtEmployeeID.setText(""); // Clear ID field
        administration.txtEmployeeName.setText(""); // Clear name field
        administration.txtEmployeePassword.setText(""); // Clear password field
        administration.txtEmployeeUser.setText(""); // Clear username field
    }

    public void loadEmployees() {
        // Load employees from database and display in the table
        if (isUserAuthorized()) { // Check if user has the necessary role
            Map<Integer, Users> employees = employeeActions.listEmployees(employeeActions.nameAdditionalValidation(administration.txtSearchEmployee.getText().trim()));
            model = (DefaultTableModel) administration.employeesTable.getModel();
            Object[] row = new Object[7]; // Array to hold employee data for a row
            for (Map.Entry<Integer, Users> entry : employees.entrySet()) {
                Users value = entry.getValue();
                // Exclude Owner and current logged-in employee from the list
                if (!value.getEmployeeRol().equals("Owner") && value.getEmployeeID() != employeeActions.getCurrentEmployee().getEmployeeID()) {
                    row[0] = value.getEmployeeID(); // Users ID
                    row[1] = value.getEmployeeName(); // Users Name
                    row[2] = value.getEmployeeUser(); // Users Username
                    row[3] = value.getEmployeeAddress(); // Users Address
                    row[4] = value.getEmployeeEmail(); // Users Email
                    row[5] = value.getEmployeeRol(); // Users Role
                    row[6] = value.getEmployeePassword(); // Users Password
                    model.addRow(row); // Add row to the table model
                }
            }
        }
    }

    private boolean isUserAuthorized() {
        // Check if the current employee has the required role (Administrator or Owner)
        String role = employeeActions.getCurrentEmployee().getEmployeeRol();
        return role.equals("Administrator") || role.equals("Owner");
    }

    private void registerEmployee() {
        // Register a new employee and refresh data
        try {
            // Validate email format
            if (!employeeActions.emailValidation(administration.txtEmployeeEmail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } // Check if email is unique
            else if (!employeeActions.isEmailUnique(administration.txtEmployeeEmail.getText().trim().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } // Validate username
            else if (!employeeActions.userValidation(administration.txtEmployeeUser.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Username already in use.");
            } // Proceed to add the employee if all validations pass
            else {
                employeeActions.addEmployee(administration.txtEmployeeUser.getText().trim(),
                        administration.txtEmployeePassword.getText(),
                        employeeActions.nameAdditionalValidation(administration.txtEmployeeName.getText().trim()),
                        administration.txtEmployeeAddress.getText().trim(),
                        administration.txtEmployeeEmail.getText().trim().toLowerCase(),
                        administration.cmbEmployeeRol.getSelectedItem().toString());
                refreshEmployeeData(); // Refresh the employee data in the view
                JOptionPane.showMessageDialog(null, "Employee registered successfully."); // Success message
            }
        } catch (Exception ex) {
            handleException(ex, "An error occurred during the process."); // Handle exceptions
        }
    }

    private boolean areFieldsClean() {
        // Check if any employee input field is blank
        return administration.txtEmployeeAddress.getText().isBlank()
                || administration.txtEmployeeEmail.getText().isBlank()
                || administration.txtEmployeeName.getText().isBlank()
                || administration.txtEmployeePassword.getText().isBlank()
                || administration.txtEmployeeUser.getText().isBlank();
    }

    private void refreshEmployeeData() {
        // Refresh employee table and fields
        cleanTable(); // Clear the table
        cleanFields(); // Clear the input fields
        loadEmployees(); // Load employees again
    }

    private void handleException(Exception ex, String message) {
        // Log the exception and show an error message
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, message); // Show error message
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions
        Object source = e.getSource();
        if (source == administration.btnEmployeeRegister) {
            // Check if fields are clean before registering a new employee
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                registerEmployee(); // Proceed to register the employee
            }
        } else if (source == administration.btnEmployeeUpdate) {
            // Ensure a row is selected before updating
            int row = administration.employeesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the employee's row."); // Error message for no selection
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                modifyEmployee(); // Proceed to modify the employee
            }
        } else if (source == administration.btnEmployeeDelete) {
            // Ensure a row is selected before deleting
            int row = administration.employeesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the employee's row."); // Error message for no selection
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Error message for empty fields
            } else {
                deleteEmployee(); // Proceed to delete the employee
            }
        } else if (source == administration.btnEmployeeCancel) {
            // Refresh employee data on cancel
            refreshEmployeeData();
            administration.btnEmployeeRegister.setEnabled(true); // Enable the register button
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle table row click to populate employee fields
        if (e.getSource() == administration.employeesTable) {
            int row = administration.employeesTable.rowAtPoint(e.getPoint());
            // Populate fields with selected employee data
            administration.txtEmployeeID.setText(administration.employeesTable.getValueAt(row, 0).toString());
            administration.txtEmployeeName.setText(administration.employeesTable.getValueAt(row, 1).toString());
            administration.txtEmployeeUser.setText(administration.employeesTable.getValueAt(row, 2).toString());
            administration.txtEmployeeAddress.setText(administration.employeesTable.getValueAt(row, 3).toString());
            administration.txtEmployeeEmail.setText(administration.employeesTable.getValueAt(row, 4).toString());
            administration.cmbEmployeeRol.setSelectedItem(administration.employeesTable.getValueAt(row, 5).toString());
            administration.txtEmployeePassword.setText(administration.employeesTable.getValueAt(row, 6).toString());
            administration.btnEmployeeRegister.setEnabled(false); // Disable register button when editing
        } else if (e.getSource() == administration.lblEmployees) {
            // Switch to the employees tab if user is authorized
            if (isUserAuthorized()) {
                administration.jTabbedPanePanels.setSelectedIndex(3);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release event in the search field
        if (e.getSource() == administration.txtSearchEmployee) {
            cleanTable(); // Clear the table before loading new results
            loadEmployees(); // Load employees based on search input
        }
    }
}
