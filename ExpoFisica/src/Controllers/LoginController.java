package Controllers;

import Models.UsersActions;
import Views.Administration;
import Views.Instructions;
import Views.Login;
import Views.Register;
import Views.TermsAndConditions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginController implements ActionListener {

    private final UsersActions employeeActions; // Handles employee-related actions
    private final Login login; // The login view instance

    public LoginController(UsersActions employeeActions, Login login) {
        this.employeeActions = employeeActions;
        this.login = login;
        // Add action listeners to buttons
        addActionListeners();
    }

    private void addActionListeners() {
        // Set up action listeners for the login buttons
        login.btnInstructions.addActionListener(this);
        login.btnRegister.addActionListener(this);
        login.btnStart.addActionListener(this);
        login.btnTermsAndConditions.addActionListener(this);
    }

    private void cleanFields() {
        // Clear the text fields for user and password
        login.txtUser.setText("");
        login.txtPassword.setText("");
    }

    private void openAdministration() throws UnsupportedLookAndFeelException {
        // Create an instance of the Administration view
        Administration administration = new Administration();
        // Check the current employee's role
        if (employeeActions.getCurrentEmployee().getEmployeeRol().equals("Auxiliary")) {
            // Disable certain features for users with the Auxiliary role
            administration.lblEmployees.setEnabled(false);
            administration.lblReports.setEnabled(false);
            administration.jTabbedPanePanels.setEnabledAt(3, false);
            administration.jTabbedPanePanels.setEnabledAt(6, false);
        }
        // Display the administration view
        administration.setVisible(true);
    }

    private void openView(Class<?> viewClass) {
        try {
            // Create an instance of the specified view class and make it visible
            Object view = viewClass.getDeclaredConstructor().newInstance();
            ((javax.swing.JFrame) view).setVisible(true);
            // Close the current login view
            login.dispose();
        } catch (Exception ex) {
            // Log any exceptions that occur during view instantiation
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Determine which button was clicked
        Object source = e.getSource();
        if (source == login.btnInstructions) {
            // Open the Instructions view
            openView(Instructions.class);
        } else if (source == login.btnTermsAndConditions) {
            // Open the Terms and Conditions view
            openView(TermsAndConditions.class);
        } else if (source == login.btnRegister) {
            // Open the Register view
            openView(Register.class);
        } else if (source == login.btnStart) {
            // Handle the login process when the Start button is clicked
            handleLogin();
        }
    }

    private void handleLogin() {
        // Handle the login process
        String username = login.txtUser.getText(); // Get the entered username
        String password = String.valueOf(login.txtPassword.getPassword()); // Get the entered password
        if (username.isBlank() || password.isBlank()) {
            // Show an error message if fields are empty
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
        } else if (employeeActions.employeeLogin(username, password)) {
            // If login is successful
            try {
                cleanFields(); // Clear the input fields
                openAdministration(); // Open the administration view
                login.dispose(); // Close the login view
            } catch (UnsupportedLookAndFeelException ex) {
                // Log any exceptions that occur while opening the administration view
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Show an error message if login fails
            JOptionPane.showMessageDialog(null, "The user and password do not match or are not registered.");
        }
    }
}
