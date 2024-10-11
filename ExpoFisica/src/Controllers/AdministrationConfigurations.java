package Controllers;

import Models.Users;
import Models.UsersActions;
import Views.Administration;
import Views.Login;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

public class AdministrationConfigurations implements MouseListener, ActionListener {

    private final Administration administration; // The administration view instance
    private final UsersActions employeeActions; // Handles employee-related actions

    public AdministrationConfigurations(Administration administration, UsersActions employeeActions) {
        this.administration = administration;
        this.employeeActions = employeeActions;
        // Adding MouseListeners to various labels in the administration view
        addMouseListeners();
        // Adding ActionListeners to buttons in the administration view
        addActionListeners();
        // Initialize the profile view with employee data
        makeProfile();
    }

    private void addMouseListeners() {
        // Attach mouse listeners to the navigation labels
        administration.lblConsumeE.addMouseListener(this);
        administration.lblCampoE.addMouseListener(this);
        administration.lblLeyCoulomb.addMouseListener(this);
        administration.lblEmployees.addMouseListener(this);
        administration.lblTorque.addMouseListener(this);
        administration.lblPotencialE.addMouseListener(this);

        administration.lblConfigurations.addMouseListener(this);
        administration.lblInstructions.addMouseListener(this);
    }

    private void addActionListeners() {
        // Attach action listeners to buttons
        administration.btnBack.addActionListener(this);
        administration.btnModifyPasswordActual.addActionListener(this);
        administration.btnChangePersonalData.addActionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Change the selected tab based on the clicked label
        if (e.getSource() == administration.lblConfigurations) {
            administration.jTabbedPanePanels.setSelectedIndex(7); // Select configurations tab
        } else if (e.getSource() == administration.lblInstructions) {
            administration.jTabbedPanePanels.setSelectedIndex(8); // Select instructions tab
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
        // Change background color when mouse enters a label
        Color backgroundColor = new Color(102, 85, 255); // Define hover color
        setPanelBackground(e, backgroundColor); // Set the background color of the panel
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Reset background color when mouse exits a label
        Color backgroundColor = new Color(102, 102, 255); // Define default color
        setPanelBackground(e, backgroundColor); // Set the background color of the panel
    }

    private void setPanelBackground(MouseEvent e, Color color) {
        // Set the background color for the corresponding panel based on the source
        if (e.getSource() == administration.lblConsumeE) {
            administration.JPanelConsumeE.setBackground(color);
        } else if (e.getSource() == administration.lblCampoE) {
            administration.JPanelPurchases.setBackground(color);
        } else if (e.getSource() == administration.lblLeyCoulomb) {
            administration.JPanelCustomers.setBackground(color);
        } else if (e.getSource() == administration.lblEmployees) {
            administration.JPanelEmployees.setBackground(color);
        } else if (e.getSource() == administration.lblTorque) {
            administration.JPanelSuppliers.setBackground(color);
        } else if (e.getSource() == administration.lblPotencialE) {
            administration.JPanelCategories.setBackground(color);
        } else if (e.getSource() == administration.lblConfigurations) {
            administration.JPanelConfigurations.setBackground(color);
        } else if (e.getSource() == administration.lblInstructions) {
            administration.JPanelInstructions.setBackground(color);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
        if (e.getSource() == administration.btnBack) {
            handleBackButton(); // Navigate back to the login screen
        } else if (e.getSource() == administration.btnModifyPasswordActual) {
            handleChangePassword(); // Handle password change
        } else if (e.getSource() == administration.btnChangePersonalData) {
            handleChangePersonalData(); // Handle personal data change
        }
    }

    private void handleBackButton() {
        try {
            // Create a new Login instance and display it
            Login login = new Login();
            login.btnRegister.setEnabled(false); // Disable register button
            login.setVisible(true); // Show the login view
            this.administration.dispose(); // Close the administration view
        } catch (UnsupportedLookAndFeelException ex) {
            // Log any exceptions that occur
            Logger.getLogger(AdministrationConfigurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleChangePassword() {
        // Validate password fields before changing the password
        if (administration.txtEmployeePasswordConfiguration.getText().isBlank()
                || administration.txtEmployeePasswordConfirm.getText().isBlank()) {
            // Show an error message if fields are empty
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
        } else if (administration.txtEmployeePasswordConfiguration.getText()
                .equals(administration.txtEmployeePasswordConfirm.getText()) && employeeActions
                .modifyPassword(Integer.valueOf(administration.txtEmployeeIDConfigurations.getText()),
                        administration.txtEmployeePasswordConfirm.getText())) {
            cleanFields(); // Clear password fields after successful change
            JOptionPane.showMessageDialog(null, "Password successfully modified."); // Success message
        }
    }

    private void handleChangePersonalData() {
        // Update the current employee's personal data
        Users employee = employeeActions.getCurrentEmployee();
        employee.setEmployeeName(administration.txtEmployeeNameConfigurations.getText().trim()); // Set name
        employee.setEmployeeEmail(administration.txtEmployeeEmailConfigurations.getText().trim().toLowerCase()); // Set email
        employee.setEmployeeAddress(administration.txtEmployeeAddressConfigurations.getText().trim()); // Set address
        JOptionPane.showMessageDialog(null, "Successful changes"); // Success message
    }

    private void cleanFields() {
        // Clear the password configuration fields
        administration.txtEmployeePasswordConfiguration.setText("");
        administration.txtEmployeePasswordConfirm.setText("");
    }

    private void makeProfile() {
        // Initialize the profile view with the current employee's information
        Users employee = employeeActions.getCurrentEmployee();
        administration.lblEmployeeNameUser.setText(employee.getEmployeeUser()); // Set username label
        administration.lblEmployeeRol.setText(employee.getEmployeeRol()); // Set role label
        administration.txtEmployeeIDConfigurations.setText(String.valueOf(employee.getEmployeeID())); // Set ID
        administration.txtEmployeeNameConfigurations.setText(employee.getEmployeeName()); // Set name
        administration.txtEmployeeEmailConfigurations.setText(employee.getEmployeeEmail()); // Set email
        administration.txtEmployeeAddressConfigurations.setText(employee.getEmployeeAddress()); // Set address
    }
}
