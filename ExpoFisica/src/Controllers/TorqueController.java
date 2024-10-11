package Controllers;

import Models.Torque;
import Models.TorqueActions;
import Views.Administration;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para la gestión de Torques en la interfaz de administración. Esta
 * clase maneja las interacciones del usuario con la interfaz gráfica
 * relacionada con los torques, incluyendo la creación, modificación,
 * eliminación y visualización de torques.
 */
public class TorqueController implements ActionListener, MouseListener, KeyListener {

    // Referencias a la vista y al modelo
    private final Administration administration;
    private final TorqueActions torqueActions;
    private DefaultTableModel model;

    // Constantes para mensajes de error
    private static final String ERROR_NUMERIC_INPUT = "Recuerda que en el campo correspondiente a %s\nse deben ingresar datos numéricos.";
    private static final String ERROR_EMPTY_FIELDS = "Por favor, rellena todos los campos.";
    private static final String ERROR_SELECT_ROW = "Por favor, selecciona una fila de Torque.";

    /**
     * Constructor del controlador.
     *
     * @param administration La vista de administración.
     * @param torqueActions El modelo de acciones de torque.
     */
    public TorqueController(Administration administration, TorqueActions torqueActions) {
        this.administration = administration;
        this.torqueActions = torqueActions;
        initializeListeners();
    }

    /**
     * Inicializa todos los listeners necesarios para la interacción con la
     * interfaz.
     */
    private void initializeListeners() {
        addActionListeners();
        addMouseListeners();
        addKeyListeners();
    }

    /**
     * Agrega los action listeners a los botones relevantes.
     */
    private void addActionListeners() {
        administration.btnRegistrarTorque.addActionListener(this);
        administration.btnModificarTorque.addActionListener(this);
        administration.btnEliminarTorque.addActionListener(this);
        administration.btnCancelarTorque.addActionListener(this);
    }

    /**
     * Agrega los mouse listeners a los componentes relevantes.
     */
    private void addMouseListeners() {
        administration.lblTorque.addMouseListener(this);
        administration.torqueTable.addMouseListener(this);
    }

    /**
     * Agrega los key listeners a los campos de texto relevantes.
     */
    private void addKeyListeners() {
        administration.txtTorqueAngulo.addKeyListener(this);
        administration.txtTorqueCarga.addKeyListener(this);
        administration.txtTorqueDistancia.addKeyListener(this);
    }

    /**
     * Verifica si alguno de los campos principales está vacío.
     *
     * @return true si algún campo está vacío, false en caso contrario.
     */
    private boolean areFieldsEmpty() {
        return administration.cmbCampoE2.getSelectedItem().toString().isEmpty()
                || administration.txtTorqueAngulo.getText().isEmpty()
                || administration.txtTorqueDistancia.getText().isEmpty();
    }

    /**
     * Limpia la tabla de torques, eliminando todas las filas.
     */
    private void cleanTable() {
        model = (DefaultTableModel) administration.torqueTable.getModel();
        model.setRowCount(0);
    }

    /**
     * Limpia todos los campos de entrada de torques.
     */
    private void cleanFields() {
        administration.txtTorqueID.setText("");
        administration.txtTorqueAngulo.setText("");
        administration.txtTorqueCarga.setText("");
        administration.txtTorqueDistancia.setText("");
        administration.txtTorqueResultado.setText("");
    }

    /**
     * Carga todos los torques desde el modelo y los muestra en la tabla.
     */
    public void loadTorques() {
        Map<Integer, Torque> torques = torqueActions.listTorques();
        model = (DefaultTableModel) administration.torqueTable.getModel();
        torques.values().forEach(torque -> model.addRow(new Object[]{
            torque.getId(),
            torque.getCampoE(),
            torque.getCargaQ(),
            torque.getDistanciaR(),
            torque.getAnguloA(),
            torque.getResultado()
        }));
    }

    /**
     * Actualiza la vista limpiando la tabla y los campos, y vuelve a cargar los
     * torques.
     */
    private void refreshTorqueData() {
        cleanTable();
        cleanFields();
        loadTorques();
    }

    /**
     * Registra un nuevo torque con los datos ingresados en los campos.
     */
    private void registerTorque() {
        if (validateNumericInputs()) {
            torqueActions.addTorque(
                    getDoubleFromComponent(administration.cmbCampoE2),
                    getDoubleFromComponent(administration.txtTorqueCarga),
                    getDoubleFromComponent(administration.txtTorqueDistancia),
                    getDoubleFromComponent(administration.txtTorqueAngulo),
                    getDoubleFromComponent(administration.txtTorqueResultado)
            );
            refreshTorqueData();
            JOptionPane.showMessageDialog(null, "Torque registrado con éxito.");
        }
    }

    /**
     * Modifica un torque existente con los datos ingresados en los campos.
     */
    private void modifyTorque() {
        if (validateNumericInputs()) {
            torqueActions.modifyTorque(
                    Integer.parseInt(administration.txtTorqueID.getText().trim()),
                    getDoubleFromComponent(administration.cmbCampoE2),
                    getDoubleFromComponent(administration.txtTorqueCarga),
                    getDoubleFromComponent(administration.txtTorqueDistancia),
                    getDoubleFromComponent(administration.txtTorqueAngulo),
                    getDoubleFromComponent(administration.txtTorqueResultado)
            );
            refreshTorqueData();
            administration.btnRegistrarTorque.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Torque modificado con éxito.");
        }
    }

    /**
     * Elimina el torque seleccionado.
     */
    private void deleteTorque() {
        int id = Integer.parseInt(administration.txtTorqueID.getText().trim());
        if (torqueActions.deleteTorque(id)) {
            refreshTorqueData();
            administration.btnRegistrarTorque.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Torque eliminado con éxito.");
        }
    }

    /**
     * Valida que todos los campos numéricos contengan valores válidos.
     *
     * @return true si todos los campos son válidos, false en caso contrario.
     */
    private boolean validateNumericInputs() {
        return validateNumericInput(administration.txtTorqueCarga, "la carga")
                && validateNumericInput(administration.txtTorqueAngulo, "el ángulo")
                && validateNumericInput(administration.txtTorqueDistancia, "la distancia");
    }

    /**
     * Valida que un campo específico contenga un valor numérico válido.
     *
     * @param field El campo a validar.
     * @param fieldName El nombre del campo para el mensaje de error.
     * @return true si el campo es válido, false en caso contrario.
     */
    private boolean validateNumericInput(JTextField field, String fieldName) {
        if (!torqueActions.isDoubleString(field.getText().trim())) {
            JOptionPane.showMessageDialog(null, String.format(ERROR_NUMERIC_INPUT, fieldName));
            return false;
        }
        return true;
    }

    /**
     * Obtiene un valor double de un componente de la interfaz.
     *
     * @param component El componente (JTextField o JComboBox) del que obtener
     * el valor.
     * @return El valor double del componente.
     */
    private double getDoubleFromComponent(JComponent component) {
        String text = (component instanceof JTextField)
                ? ((JTextField) component).getText()
                : ((JComboBox<?>) component).getSelectedItem().toString();
        return Double.parseDouble(text.trim());
    }

    /**
     * Maneja los eventos de acción de los botones.
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnRegistrarTorque) {
            handleRegisterTorque();
        } else if (source == administration.btnModificarTorque) {
            handleModifyTorque();
        } else if (source == administration.btnEliminarTorque) {
            handleDeleteTorque();
        } else if (source == administration.btnCancelarTorque) {
            handleCancelTorque();
        }
    }

    /**
     * Maneja el evento de registrar un nuevo torque.
     */
    private void handleRegisterTorque() {
        if (areFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, ERROR_EMPTY_FIELDS);
        } else {
            registerTorque();
        }
    }

    /**
     * Maneja el evento de modificar un torque existente.
     */
    private void handleModifyTorque() {
        if (administration.torqueTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, ERROR_SELECT_ROW);
        } else if (areFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, ERROR_EMPTY_FIELDS);
        } else {
            modifyTorque();
        }
    }

    /**
     * Maneja el evento de eliminar un torque.
     */
    private void handleDeleteTorque() {
        if (administration.torqueTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, ERROR_SELECT_ROW);
        } else {
            deleteTorque();
        }
    }

    /**
     * Maneja el evento de cancelar la operación actual.
     */
    private void handleCancelTorque() {
        refreshTorqueData();
        administration.btnRegistrarTorque.setEnabled(true);
    }

    /**
     * Maneja los eventos de clic del ratón.
     *
     * @param e El evento del ratón.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.torqueTable) {
            handleTorqueTableClick(e);
        } else if (e.getSource() == administration.lblTorque) {
            administration.jTabbedPanePanels.setSelectedIndex(4);
        }
    }

    /**
     * Maneja el evento de clic en la tabla de torques.
     *
     * @param e El evento del ratón.
     */
    private void handleTorqueTableClick(MouseEvent e) {
        int row = administration.torqueTable.rowAtPoint(e.getPoint());
        if (row >= 0) {
            fillFieldsFromTableRow(row);
            administration.btnRegistrarTorque.setEnabled(false);
        }
    }

    /**
     * Rellena los campos de entrada con los datos de la fila seleccionada en la
     * tabla.
     *
     * @param row El índice de la fila seleccionada.
     */
    private void fillFieldsFromTableRow(int row) {
        administration.txtTorqueID.setText(getValueFromTable(row, 0));
        administration.cmbCampoE2.setSelectedItem(getValueFromTable(row, 1));
        administration.txtTorqueCarga.setText(getValueFromTable(row, 2));
        administration.txtTorqueDistancia.setText(getValueFromTable(row, 3));
        administration.txtTorqueAngulo.setText(getValueFromTable(row, 4));
        administration.txtTorqueResultado.setText(getValueFromTable(row, 5));
    }

    /**
     * Obtiene el valor de una celda específica de la tabla como String.
     *
     * @param row La fila de la tabla.
     * @param column La columna de la tabla.
     * @return El valor de la celda como String, o una cadena vacía si es null.
     */
    private String getValueFromTable(int row, int column) {
        return Optional.ofNullable(administration.torqueTable.getValueAt(row, column))
                .map(Object::toString)
                .orElse("");
    }

    // Métodos no utilizados de la interfaz MouseListener
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

    // Métodos no utilizados de la interfaz KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Maneja el evento de soltar una tecla en los campos de entrada.
     *
     * @param e El evento de teclado.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == administration.txtTorqueAngulo
                || e.getSource() == administration.txtTorqueCarga
                || e.getSource() == administration.txtTorqueDistancia) {
            calculateTorque();
        }
    }

    /**
     * Calcula el torque basado en los valores ingresados en los campos.
     */
    private void calculateTorque() {
        if (areAllFieldsFilled() && areAllFieldsNumeric()) {
            double angulo = getDoubleFromComponent(administration.txtTorqueAngulo);
            double carga = getDoubleFromComponent(administration.txtTorqueCarga);
            double distancia = getDoubleFromComponent(administration.txtTorqueDistancia);
            double campoElectrico = getDoubleFromComponent(administration.cmbCampoE2);

            double anguloEnRadianes = Math.toRadians(angulo);
            double momentoDipolar = carga * distancia;
            double torque = momentoDipolar * campoElectrico * Math.sin(anguloEnRadianes);

            administration.txtTorqueResultado.setText(String.valueOf(torque));
        }
    }

    /**
     * Verifica si todos los campos necesarios para el cálculo están llenos.
     *
     * @return true si todos los campos están llenos, false en caso contrario.
     */
    private boolean areAllFieldsFilled() {
        return !administration.txtTorqueAngulo.getText().isEmpty()
                && !administration.txtTorqueCarga.getText().isEmpty()
                && !administration.txtTorqueDistancia.getText().isEmpty()
                && !administration.cmbCampoE2.getSelectedItem().toString().isEmpty();
    }

    /**
     * Verifica si todos los campos numéricos contienen valores numéricos
     * válidos.
     *
     * @return true si todos los campos son numéricos, false en caso contrario.
     */
    private boolean areAllFieldsNumeric() {
        return torqueActions.isDoubleString(administration.txtTorqueAngulo.getText().trim())
                && torqueActions.isDoubleString(administration.txtTorqueCarga.getText().trim())
                && torqueActions.isDoubleString(administration.txtTorqueDistancia.getText().trim());
    }
}
