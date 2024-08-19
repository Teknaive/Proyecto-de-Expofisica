package Controllers;

import Models.ElectricConsume;
import Models.ElectricConsumeActions;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ElectricConsumeController implements ActionListener, MouseListener, KeyListener {
    
    private final Administration administration;
    private final ElectricConsumeActions electricConsumeActions;
    private final UsersActions employeeActions;
    private DefaultTableModel model;
    
    public ElectricConsumeController(Administration administration, ElectricConsumeActions electricConsumeActions, UsersActions employeeActions) {
        this.administration = administration;
        this.electricConsumeActions = electricConsumeActions;
        this.employeeActions = employeeActions;
        // Add action listeners to buttons
        addActionListeners();
        // Add mouse listeners to components
        addMouseListeners();
        // Add key listeners to search field
        addKeyListeners();
    }
    
    private void addActionListeners() {
        // Add action listeners to product-related buttons
        administration.btnConsumeRegister.addActionListener(this);
        administration.btnConsumeModify.addActionListener(this);
        administration.btnConsumeDelete.addActionListener(this);
        administration.btnConsumeCancel.addActionListener(this);
    }
    
    private void addMouseListeners() {
        // Add mouse listeners to product-related components
        administration.lblConsumeE.addMouseListener(this);
        administration.ConsumeTable.addMouseListener(this);
    }
    
    private void addKeyListeners() {
        // Add key listener to the search product field
        administration.txtSearchDevice.addKeyListener(this);
        administration.txtConsumeResult.addKeyListener(this);
    }
    
    private boolean areFieldsClean() {
        // Check if any product input field is blank
        return administration.txtConsumeDevice.getText().isBlank()
                || administration.txtConsumePower.getText().isBlank()
                || administration.txtConsumeTariff.getText().isBlank()
                || administration.txtConsumeTime.getText().isBlank();
    }
    
    private void cleanTable() {
        // Clear the product table and combo box
        model = (DefaultTableModel) administration.ConsumeTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove all rows from the table
        }
    }
    
    private void cleanFields() {
        // Clear product input fields
        administration.txtConsumeID.setText("");
        administration.txtConsumeResult.setText("");
        administration.txtConsumePower.setText("");
        administration.txtConsumeTariff.setText("");
        administration.txtConsumeDevice.setText("");
        administration.txtConsumeTime.setText("");
    }
    
    private void registerConsume() {
        if (!electricConsumeActions.isDoubleString(administration.txtConsumePower.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la potencia\nse deben ingresar datos numéricos.");
        } else if (!electricConsumeActions.isDoubleString(administration.txtConsumeTime.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente al tiempo\nse deben ingresar datos numéricos.");
        } else if (!electricConsumeActions.isDoubleString(administration.txtConsumeTariff.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la tarifa\nse deben ingresar datos numéricos.");
        } else {
            Users employee = employeeActions.getCurrentEmployee();
            electricConsumeActions.addElectricConsume(electricConsumeActions.nameAdditionalValidation(administration.txtConsumeDevice.getText().trim()), Double.valueOf(administration.txtConsumePower.getText().trim()), Double.valueOf(administration.txtConsumeTime.getText().trim()), Double.valueOf(administration.txtConsumeTariff.getText().trim()), employee.getEmployeeUser());
            refreshConsumeData();
            JOptionPane.showMessageDialog(null, "Consumo eléctrico registrado con éxito.");
        }
    }
    
    private void modifyElectricConsume() {
        if (!electricConsumeActions.isDoubleString(administration.txtConsumePower.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la potencia\nse deben ingresar datos numéricos.");
        } else if (!electricConsumeActions.isDoubleString(administration.txtConsumeTime.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente al tiempo\nse deben ingresar datos numéricos.");
        } else if (!electricConsumeActions.isDoubleString(administration.txtConsumeTariff.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la tarifa\nse deben ingresar datos numéricos.");
        } else if (electricConsumeActions.modifyElectricConsume(Integer.valueOf(administration.txtConsumeID.getText()),
                electricConsumeActions.nameAdditionalValidation(administration.txtConsumeDevice.getText().trim()),
                Double.valueOf(administration.txtConsumePower.getText().trim()),
                Double.valueOf(administration.txtConsumeTime.getText().trim()),
                Double.valueOf(administration.txtConsumeTariff.getText().trim()),
                employeeActions.getCurrentEmployee().getEmployeeUser())) {
            refreshConsumeData();
            administration.btnConsumeRegister.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Consumo modificado con éxito.");
        }
    }
    
    private void deleteElectricConsume() {
        if (electricConsumeActions.deleteElectricConsume(Integer.valueOf(administration.txtConsumeID.getText()))) {
            refreshConsumeData();
            administration.txtPurchaseProductID.setText("");
            administration.btnConsumeRegister.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Consumo eliminado con éxito.");
        }
    }
    
    private void refreshConsumeData() {
        cleanTable();
        cleanFields();
        loadConsume();
    }
    
    public void loadConsume() {
        Map<Integer, ElectricConsume> electricConsumes = electricConsumeActions.listElectricConsume(electricConsumeActions.nameAdditionalValidation(administration.txtSearchDevice.getText().trim()));
        model = (DefaultTableModel) administration.ConsumeTable.getModel();
        Object[] row = new Object[6];
        for (Map.Entry<Integer, ElectricConsume> entry : electricConsumes.entrySet()) {
            ElectricConsume value = entry.getValue();
            if (value.getUser().equals(employeeActions.getCurrentEmployee().getEmployeeUser())) {
                row[0] = value.getID();
                row[1] = value.getElectricAppliance();
                row[2] = value.getElectricPower();
                row[3] = value.getDeviceTime();
                row[4] = value.getElectricityTariff();
                row[5] = value.getResult();
                model.addRow(row);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnConsumeRegister) {
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Show error if fields are blank
            } else {
                registerConsume();
            }
        } else if (source == administration.btnConsumeModify) {
            // Handle modify product button
            int row = administration.ConsumeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the product's row."); // Show error if no row is selected
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Show error if fields are blank
            } else {
                modifyElectricConsume();
            }
        } else if (source == administration.btnConsumeDelete) {
            // Handle delete product button
            int row = administration.ConsumeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select the product's row."); // Show error if no row is selected
            } else {
                deleteElectricConsume();
            }
        } else if (source == administration.btnConsumeCancel) {
            // Handle cancel button
            refreshConsumeData();
            administration.btnConsumeRegister.setEnabled(true); // Enable the register button
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.ConsumeTable) {
            int row = administration.ConsumeTable.rowAtPoint(e.getPoint()); // Get the clicked row
            administration.txtConsumeID.setText(administration.ConsumeTable.getValueAt(row, 0).toString());
            administration.txtConsumeDevice.setText(administration.ConsumeTable.getValueAt(row, 1).toString());
            administration.txtConsumePower.setText(administration.ConsumeTable.getValueAt(row, 2).toString());
            administration.txtConsumeTime.setText(administration.ConsumeTable.getValueAt(row, 3).toString());
            administration.txtConsumeTariff.setText(administration.ConsumeTable.getValueAt(row, 4).toString());
            administration.txtConsumeResult.setText(administration.ConsumeTable.getValueAt(row, 5).toString());
            administration.btnConsumeRegister.setEnabled(false); // Disable the register button
        } else if (e.getSource() == administration.lblConsumeE) {
            // Switch to the product management tab
            administration.jTabbedPanePanels.setSelectedIndex(0);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == administration.txtSearchDevice) {
            cleanTable();
            loadConsume();
        } else if (e.getSource() == administration.txtConsumeResult) {
            // Calculate the total price based on the product quantity
            if (!administration.txtConsumePower.getText().isBlank()
                    && electricConsumeActions.isDoubleString(administration.txtConsumePower.getText().trim())
                    && !administration.txtConsumeTime.getText().isBlank()
                    && electricConsumeActions.isDoubleString(administration.txtConsumeTime.getText().trim())
                    && !administration.txtConsumeTariff.getText().isBlank()
                    && electricConsumeActions.isDoubleString(administration.txtConsumeTariff.getText().trim())) {
                Double electricConsumePower = Double.valueOf(administration.txtConsumePower.getText().trim());
                Double electricConsumeTime = Double.valueOf(administration.txtConsumeTime.getText().trim());
                Double electricConsumeTariff = Double.valueOf(administration.txtConsumeTariff.getText().trim());
                String result = String.valueOf(electricConsumePower * electricConsumeTime * electricConsumeTariff);
                administration.txtConsumeResult.setText(result);
            }
        }
    }
    
}
