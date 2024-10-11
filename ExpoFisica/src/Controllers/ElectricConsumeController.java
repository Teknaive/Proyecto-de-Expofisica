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

/**
 * Controlador para la gestión de consumos eléctricos. Maneja las interacciones
 * del usuario en la vista de administración.
 */
public class ElectricConsumeController implements ActionListener, MouseListener, KeyListener {

    private static final String ERROR_EMPTY_FIELDS = "Por favor, complete todos los campos.";
    private static final String ERROR_NUMERIC_FIELDS = "Recuerda que en el campo correspondiente a %s se deben ingresar datos numéricos.";
    private static final String SUCCESS_REGISTER = "Consumo eléctrico registrado con éxito.";
    private static final String SUCCESS_MODIFY = "Consumo modificado con éxito.";
    private static final String SUCCESS_DELETE = "Consumo eliminado con éxito.";

    private final Administration administration;
    private final ElectricConsumeActions electricConsumeActions;
    private final UsersActions employeeActions;
    private DefaultTableModel model;

    public ElectricConsumeController(Administration administration, ElectricConsumeActions electricConsumeActions, UsersActions employeeActions) {
        this.administration = administration;
        this.electricConsumeActions = electricConsumeActions;
        this.employeeActions = employeeActions;
        // Inicializar listeners
        initializeListeners();
    }

    private void initializeListeners() {
        // Asignar listeners a botones
        addActionListeners();
        addMouseListeners();
        addKeyListeners();
    }

    private void addActionListeners() {
        administration.btnConsumeRegister.addActionListener(this);
        administration.btnConsumeModify.addActionListener(this);
        administration.btnConsumeDelete.addActionListener(this);
        administration.btnConsumeCancel.addActionListener(this);
    }

    private void addMouseListeners() {
        administration.lblConsumeE.addMouseListener(this);
        administration.ConsumeTable.addMouseListener(this);
    }

    private void addKeyListeners() {
        administration.txtSearchDevice.addKeyListener(this);
        administration.txtConsumeResult.addKeyListener(this);
    }

    private boolean areFieldsClean() {
        return administration.txtConsumeDevice.getText().isBlank()
                || administration.txtConsumePower.getText().isBlank()
                || administration.txtConsumeTariff.getText().isBlank()
                || administration.txtConsumeTime.getText().isBlank();
    }

    private void cleanTable() {
        model = (DefaultTableModel) administration.ConsumeTable.getModel();
        model.setRowCount(0); // Limpiar la tabla
    }

    private void cleanFields() {
        administration.txtConsumeID.setText("");
        administration.txtConsumeResult.setText("");
        administration.txtConsumePower.setText("");
        administration.txtConsumeTariff.setText("");
        administration.txtConsumeDevice.setText("");
        administration.txtConsumeTime.setText("");
    }

    /**
     * Valida los campos de entrada para asegurarse de que son numéricos.
     *
     * @return true si todos los campos son válidos; false en caso contrario.
     */
    private boolean validateFields() {
        if (!electricConsumeActions.isDoubleString(administration.txtConsumePower.getText().trim())) {
            showErrorMessage("potencia");
            return false;
        }
        if (!electricConsumeActions.isDoubleString(administration.txtConsumeTime.getText().trim())) {
            showErrorMessage("tiempo");
            return false;
        }
        if (!electricConsumeActions.isDoubleString(administration.txtConsumeTariff.getText().trim())) {
            showErrorMessage("tarifa");
            return false;
        }
        return true;
    }

    /**
     * Muestra un mensaje de error relacionado con campos numéricos.
     *
     * @param field El campo que no es válido.
     */
    private void showErrorMessage(String field) {
        JOptionPane.showMessageDialog(null, String.format(ERROR_NUMERIC_FIELDS, field));
    }

    private void registerConsume() {
        if (!validateFields()) {
            return; // Validar campos antes de proceder
        }
        Users employee = employeeActions.getCurrentEmployee();
        electricConsumeActions.addElectricConsume(
                electricConsumeActions.nameAdditionalValidation(administration.txtConsumeDevice.getText().trim()),
                Double.valueOf(administration.txtConsumePower.getText().trim()),
                Double.valueOf(administration.txtConsumeTime.getText().trim()),
                Double.valueOf(administration.txtConsumeTariff.getText().trim()),
                employee.getEmployeeUser()
        );
        refreshConsumeData();
        JOptionPane.showMessageDialog(null, SUCCESS_REGISTER);
    }

    private void modifyElectricConsume() {
        if (!validateFields()) {
            return; // Validar campos antes de proceder
        }
        Integer consumeId = Integer.valueOf(administration.txtConsumeID.getText());
        boolean modified = electricConsumeActions.modifyElectricConsume(
                consumeId,
                electricConsumeActions.nameAdditionalValidation(administration.txtConsumeDevice.getText().trim()),
                Double.valueOf(administration.txtConsumePower.getText().trim()),
                Double.valueOf(administration.txtConsumeTime.getText().trim()),
                Double.valueOf(administration.txtConsumeTariff.getText().trim()),
                employeeActions.getCurrentEmployee().getEmployeeUser()
        );

        if (modified) {
            refreshConsumeData();
            administration.btnConsumeRegister.setEnabled(true);
            JOptionPane.showMessageDialog(null, SUCCESS_MODIFY);
        }
    }

    private void deleteElectricConsume() {
        Integer consumeId = Integer.valueOf(administration.txtConsumeID.getText());
        if (electricConsumeActions.deleteElectricConsume(consumeId)) {
            refreshConsumeData();
            administration.txtFieldEID.setText("");
            administration.btnConsumeRegister.setEnabled(true);
            JOptionPane.showMessageDialog(null, SUCCESS_DELETE);
        }
    }

    private void refreshConsumeData() {
        cleanTable();
        cleanFields();
        loadConsume();
    }

    public void loadConsume() {
        Map<Integer, ElectricConsume> electricConsumes = electricConsumeActions.listElectricConsume(
                electricConsumeActions.nameAdditionalValidation(administration.txtSearchDevice.getText().trim())
        );

        model = (DefaultTableModel) administration.ConsumeTable.getModel();
        for (ElectricConsume consume : electricConsumes.values()) {
            if (consume.getUser().equals(employeeActions.getCurrentEmployee().getEmployeeUser())) {
                Object[] row = {
                    consume.getId(),
                    consume.getElectricAppliance(),
                    consume.getElectricPower(),
                    consume.getDeviceTime(),
                    consume.getElectricityTariff(),
                    consume.getResult()
                };
                model.addRow(row);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnConsumeRegister) {
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, ERROR_EMPTY_FIELDS); // Mostrar error si los campos están vacíos
            } else {
                registerConsume();
            }
        } else if (source == administration.btnConsumeModify) {
            handleModifyAction();
        } else if (source == administration.btnConsumeDelete) {
            handleDeleteAction();
        } else if (source == administration.btnConsumeCancel) {
            refreshConsumeData();
            administration.btnConsumeRegister.setEnabled(true); // Activar botón de registro
        }
    }

    private void handleModifyAction() {
        int row = administration.ConsumeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila del consumo."); // Mostrar error si no se selecciona fila
        } else if (areFieldsClean()) {
            JOptionPane.showMessageDialog(null, ERROR_EMPTY_FIELDS); // Mostrar error si los campos están vacíos
        } else {
            modifyElectricConsume();
        }
    }

    private void handleDeleteAction() {
        int row = administration.ConsumeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila del consumo."); // Mostrar error si no se selecciona fila
        } else {
            deleteElectricConsume();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.ConsumeTable) {
            int row = administration.ConsumeTable.rowAtPoint(e.getPoint()); // Obtener la fila seleccionada
            administration.txtConsumeID.setText(administration.ConsumeTable.getValueAt(row, 0).toString());
            administration.txtConsumeDevice.setText(administration.ConsumeTable.getValueAt(row, 1).toString());
            administration.txtConsumePower.setText(administration.ConsumeTable.getValueAt(row, 2).toString());
            administration.txtConsumeTime.setText(administration.ConsumeTable.getValueAt(row, 3).toString());
            administration.txtConsumeTariff.setText(administration.ConsumeTable.getValueAt(row, 4).toString());
            administration.txtConsumeResult.setText(administration.ConsumeTable.getValueAt(row, 5).toString());
            administration.btnConsumeRegister.setEnabled(false); // Desactivar botón de registro
        } else if (e.getSource() == administration.lblConsumeE) {
            // Cambiar a la pestaña de gestión de consumos
            administration.jTabbedPanePanels.setSelectedIndex(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No se necesita implementación
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se necesita implementación
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // No se necesita implementación
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == administration.txtSearchDevice) {
            cleanTable();
            loadConsume();
        } else if (e.getSource() == administration.txtConsumeResult) {
            calculateResult();
        }
    }

    /**
     * Calcula el resultado del consumo eléctrico basado en los valores
     * ingresados.
     */
    private void calculateResult() {
        if (areNumericFieldsFilled()) {
            double electricConsumePower = Double.parseDouble(administration.txtConsumePower.getText().trim());
            double electricConsumeTime = Double.parseDouble(administration.txtConsumeTime.getText().trim());
            double electricConsumeTariff = Double.parseDouble(administration.txtConsumeTariff.getText().trim());
            String result = String.valueOf(electricConsumePower * electricConsumeTime * electricConsumeTariff);
            administration.txtConsumeResult.setText(result);
        }
    }

    /**
     * Verifica si todos los campos numéricos están llenos y son válidos.
     *
     * @return true si todos los campos están llenos; false en caso contrario.
     */
    private boolean areNumericFieldsFilled() {
        return !administration.txtConsumePower.getText().isBlank()
                && electricConsumeActions.isDoubleString(administration.txtConsumePower.getText().trim())
                && !administration.txtConsumeTime.getText().isBlank()
                && electricConsumeActions.isDoubleString(administration.txtConsumeTime.getText().trim())
                && !administration.txtConsumeTariff.getText().isBlank()
                && electricConsumeActions.isDoubleString(administration.txtConsumeTariff.getText().trim());
    }
}
