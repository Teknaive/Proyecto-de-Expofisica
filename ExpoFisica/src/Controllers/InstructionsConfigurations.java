package Controllers;

import Models.UsersActions;
import Views.Instructions;
import Views.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class InstructionsConfigurations implements ActionListener {

    private final Instructions instructions; // Reference to the Instructions view
    private final UsersActions employeeActions; // Handles employee-related actions

    // Constructor to initialize the Instructions view and UsersActions
    public InstructionsConfigurations(Instructions instructions, UsersActions employeeActions) {
        this.instructions = instructions;
        this.employeeActions = employeeActions;
        // Add action listener to the back button
        this.instructions.btnBack.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if the event source is the back button
        if (e.getSource() == instructions.btnBack) {
            handleBackButton(); // Call method to handle back button action
        }
    }

    // Method to handle the back button action
    private void handleBackButton() {
        try {
            // Create a new instance of the Login view
            Login login = new Login();
            // If there are existing users, disable the register button
            if (employeeActions.getUserCount() != 0) {
                login.btnRegister.setEnabled(false);
            }
            login.setVisible(true); // Make the Login view visible
            this.instructions.dispose(); // Close the Instructions view
        } catch (UnsupportedLookAndFeelException ex) {
            // Log any exceptions that occur during the process
            Logger.getLogger(InstructionsConfigurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
