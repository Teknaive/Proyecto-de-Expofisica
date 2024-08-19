package Controllers;

import Models.UsersActions;
import Views.Login;
import Views.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

public class RegisterController implements ActionListener {

    private final Register register; // Reference to the Register view
    private final UsersActions employeeActions; // Reference to UsersActions model

    // Constructor to initialize the Register view and UsersActions
    public RegisterController(Register register, UsersActions employeeActions) {
        this.register = register;
        this.employeeActions = employeeActions;
        // Add action listeners to buttons
        addActionListeners();
    }

    private void addActionListeners() {
        // Add action listeners to register view buttons
        register.btnRegister.addActionListener(this);
        register.btnBack.addActionListener(this);
    }

    private void cleanFields() {
        // Clear all input fields in the register view
        register.txtUser.setText("");
        register.txtPassword.setText("");
        register.txtAddress.setText("");
        register.txtName.setText("");
        register.txtEmail.setText("");
    }

    private void registerEmployee() {
        // Register a new employee
        try {
            String email = register.txtEmail.getText().trim().toLowerCase(); // Get and trim email
            String username = register.txtUser.getText().trim(); // Get and trim username
            String name = employeeActions.nameAdditionalValidation(register.txtName.getText().trim()); // Validate name
            String password = register.txtPassword.getText(); // Get password
            String address = register.txtAddress.getText().trim(); // Get and trim address
            String role = register.cmbEmployeeRol.getSelectedItem().toString(); // Get selected role

            if (!employeeActions.emailValidation(email)) {
                // Validate email format
                JOptionPane.showMessageDialog(null, "Invalid email format.");
            } else if (!employeeActions.isEmailUnique(email)) {
                // Check if email is unique
                JOptionPane.showMessageDialog(null, "Email already in use.");
            } else if (!employeeActions.userValidation(username)) {
                // Check if username is unique
                JOptionPane.showMessageDialog(null, "Username already in use.");
            } else {
                // Add new employee to the database
                employeeActions.addEmployee(username, password, name, address, email, role);
                cleanFields(); // Clear input fields
                JOptionPane.showMessageDialog(null, "Employee registered successfully.");
                openLogin(); // Open login view
                register.dispose(); // Close register view
            }
        } catch (Exception ex) {
            // Handle any exceptions during registration
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred during registration.");
        }
    }

    private void openLogin() throws UnsupportedLookAndFeelException {
        // Open the login view
        Login login = new Login();
        // If there are existing users, disable the register button
        if (employeeActions.getUserCount() != 0) {
            login.btnRegister.setEnabled(false); // Disable register button
        }
        login.setVisible(true); // Make login view visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events for buttons
        Object source = e.getSource();
        if (source == register.btnBack) {
            // Open login view
            openLoginView();
        } else if (source == register.btnRegister) {
            // Register new employee if fields are valid
            if (areFieldsValid()) {
                registerEmployee();
            } else {
                // Show error message if fields are not valid
                JOptionPane.showMessageDialog(null, "Please fill in all fields and accept the terms and conditions.");
            }
        }
    }

    private void openLoginView() {
        // Open the login view
        try {
            openLogin(); // Call method to open login view
            register.dispose(); // Close register view
        } catch (UnsupportedLookAndFeelException ex) {
            // Handle any exceptions during opening of login view
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean areFieldsValid() {
        // Check if all fields are valid and terms are accepted
        return !register.txtAddress.getText().isBlank()
                && !register.txtEmail.getText().isBlank()
                && !register.txtName.getText().isBlank()
                && !register.txtPassword.getText().isBlank()
                && !register.txtUser.getText().trim().isBlank()
                && register.jCheckBoxTermsAndConditions.isSelected();
    }
}
