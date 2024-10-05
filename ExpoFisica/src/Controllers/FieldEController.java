package Controllers;

import Models.FieldE;
import Models.FieldEActions;
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

public class FieldEController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration;
    private final FieldEActions fieldEActions;
    private final UsersActions employeeActions;
    private DefaultTableModel model;

    public FieldEController(Administration administration, FieldEActions fieldEActions, UsersActions employeeActions) {
        this.administration = administration;
        this.fieldEActions = fieldEActions;
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
        administration.btnRegistrarCampo.addActionListener(this);
        administration.btnPurchaseBuy.addActionListener(this);
        administration.btnPurchaseDelete.addActionListener(this);
        administration.btnPurchaseCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        // Add mouse listeners to product-related components
        administration.lblPurchases.addMouseListener(this);
        administration.purchaseTable.addMouseListener(this);
    }

    private void addKeyListeners() {
        // Add key listener to the search product field

        administration.txtPurchaseTotal.addKeyListener(this);
    }

    private boolean areFieldsClean() {
        // Check if any product input field is blank
        return administration.txtPurchaseProductQuantity.getText().isBlank()
                || administration.txtPurchasePrice.getText().isBlank()
                || administration.txtPurchasePrice1.getText().isBlank()
                || administration.txtPurchaseID.getText().isBlank();
    }

    private void cleanTable() {
        // Clear the product table and combo box
        model = (DefaultTableModel) administration.purchaseTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0); // Remove all rows from the table
        }
    }

    private void cleanFields() {
        // Clear product input fields
        administration.txtPurchaseProductID.setText("");
        administration.txtPurchaseProductQuantity.setText("");
        administration.txtPurchaseID.setText("");
        administration.txtPurchasePrice.setText("");
        administration.txtPurchasePrice1.setText("");
        administration.txtPurchaseTotal.setText("");
    }

    private void registerField() {
        if (!fieldEActions.isDoubleString(administration.txtPurchaseProductQuantity.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la carga\nse deben ingresar datos numéricos.");
        } else if (!fieldEActions.isDoubleString(administration.txtPurchasePrice.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la distancia\nse deben ingresar datos numéricos.");
        } else if (!fieldEActions.isDoubleString(administration.txtPurchasePrice1.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la tarifa\nse deben ingresar datos numéricos.");
        } else {
            fieldEActions.addFieldE(Double.parseDouble(administration.txtPurchaseProductQuantity.getText().trim()),
                    Double.parseDouble(administration.txtPurchasePrice.getText().trim()), Double.parseDouble(administration.txtPurchasePrice1.getText().trim()), administration.txtPurchaseID.getText().trim());
            refreshConsumeData();
            JOptionPane.showMessageDialog(null, "Consumo eléctrico registrado con éxito.");
        }
    }

    /*private void modifyElectricConsume() {
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
    }*/
    private void refreshConsumeData() {
        cleanTable();
        cleanFields();
        loadFieldsE();
    }

    public void loadFieldsE() {
        Map<Integer, FieldE> fieldsE = fieldEActions.listFieldsE();
        model = (DefaultTableModel) administration.purchaseTable.getModel();
        Object[] row = new Object[6];
        for (Map.Entry<Integer, FieldE> entry : fieldsE.entrySet()) {
            FieldE value = entry.getValue();

            row[0] = value.getID();
            row[1] = value.getCargaQ();
            row[2] = value.getDireccion();
            row[3] = value.getDistanciaR();
            row[4] = value.getAnguloA();
            row[5] = value.getResult();
            model.addRow(row);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnRegistrarCampo) {
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields."); // Show error if fields are blank
            } else {
                registerField();
            }
        }
        /*else if (source == administration.btnConsumeModify) {
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
        } */else if (source == administration.btnPurchaseCancel) {
            // Handle cancel button
            refreshConsumeData();
            administration.btnRegistrarCampo.setEnabled(true); // Enable the register button
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.purchaseTable) {
            int row = administration.purchaseTable.rowAtPoint(e.getPoint()); // Get the clicked row
            administration.txtPurchaseProductID.setText(administration.purchaseTable.getValueAt(row, 0).toString());
            administration.txtPurchaseProductQuantity.setText(administration.purchaseTable.getValueAt(row, 1).toString());
            administration.txtPurchaseID.setText(administration.purchaseTable.getValueAt(row, 2).toString());
            administration.txtPurchasePrice.setText(administration.purchaseTable.getValueAt(row, 3).toString());
            administration.txtPurchasePrice1.setText(administration.purchaseTable.getValueAt(row, 4).toString());
            administration.txtPurchaseTotal.setText(administration.purchaseTable.getValueAt(row, 5).toString());
            administration.btnRegistrarCampo.setEnabled(false); // Disable the register button
        } else if (e.getSource() == administration.lblPurchases) {
            // Switch to the product management tab
            administration.jTabbedPanePanels.setSelectedIndex(1);
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
        if (e.getSource() == administration.txtPurchaseTotal) {
            // Calculate the total price based on the product quantity
            if (!administration.txtPurchaseProductQuantity.getText().isBlank()
                    && fieldEActions.isDoubleString(administration.txtPurchaseProductQuantity.getText().trim())
                    && !administration.txtPurchasePrice.getText().isBlank()
                    && fieldEActions.isDoubleString(administration.txtPurchasePrice.getText().trim())
                    && !administration.txtPurchasePrice1.getText().isBlank()
                    && fieldEActions.isDoubleString(administration.txtPurchasePrice1.getText().trim())) {
                double k = 8.99e9; // Constante de Coulomb en N·m²/C²
                double result;

                double cargaQ = Double.parseDouble(administration.txtPurchaseProductQuantity.getText().trim());
                double distanciaR = Double.parseDouble(administration.txtPurchasePrice.getText().trim());
                double anguloA = Double.parseDouble(administration.txtPurchasePrice1.getText().trim());

                // Calcular la magnitud del campo eléctrico
                double magnitudE = k * Math.abs(cargaQ) / (distanciaR * distanciaR);

                // Si se proporciona un ángulo, calculamos las componentes del campo
                if (anguloA != 0) {
                    double Ex = magnitudE * Math.cos(Math.toRadians(anguloA));
                    double Ey = magnitudE * Math.sin(Math.toRadians(anguloA));
                    result = Math.sqrt(Ex * Ex + Ey * Ey);
                } else {
                    result = magnitudE;
                }
                administration.txtConsumeResult.setText(String.valueOf(result));
            }
        }
    }

}
