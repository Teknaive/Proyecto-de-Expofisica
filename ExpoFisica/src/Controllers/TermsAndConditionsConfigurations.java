package Controllers;

import Models.UsersActions;
import Views.Login;
import Views.TermsAndConditions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class TermsAndConditionsConfigurations implements ActionListener {

    private final TermsAndConditions termsAndConditions; // Reference to the TermsAndConditions view
    private final UsersActions employeeActions; // Handles employee-related actions

    // Constructor to initialize the TermsAndConditions view and UsersActions
    public TermsAndConditionsConfigurations(TermsAndConditions termsAndConditions, UsersActions employeeActions) {
        this.termsAndConditions = termsAndConditions;
        this.employeeActions = employeeActions;
        // Add action listener to the back button
        this.termsAndConditions.btnBack.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if the source of the action is the back button
        if (e.getSource() == termsAndConditions.btnBack) {
            handleBackButton(); // Call method to handle back button action
        }
    }

    // Method to handle the back button action
    private void handleBackButton() {
        try {
            // Create and display the login view
            Login login = new Login();
            // If there are existing users, disable the register button
            if (employeeActions.getUserCount() != 0) {
                login.btnRegister.setEnabled(false);
            }
            login.setVisible(true); // Make the Login view visible
            this.termsAndConditions.dispose(); // Close the TermsAndConditions view
        } catch (UnsupportedLookAndFeelException ex) {
            // Log any exception that occurs during the process
            Logger.getLogger(TermsAndConditionsConfigurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
