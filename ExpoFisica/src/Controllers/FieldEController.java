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

/**
 * Controlador para gestionar la vista de campos eléctricos. Implementa
 * ActionListener, MouseListener y KeyListener para manejar eventos.
 */
public class FieldEController implements ActionListener, MouseListener, KeyListener {
    
    private final Administration administration;
    private final FieldEActions fieldEActions;
    private DefaultTableModel model;

    /**
     * Constructor para inicializar el controlador de campos eléctricos.
     *
     * @param administration La vista de administración.
     * @param fieldEActions Las acciones relacionadas con FieldE.
     * @param employeeActions Las acciones relacionadas con empleados (no se
     * utiliza aquí, pero podría usarse en el futuro).
     */
    public FieldEController(Administration administration, FieldEActions fieldEActions, UsersActions employeeActions) {
        this.administration = administration;
        this.fieldEActions = fieldEActions;
        // Registrar listeners
        addActionListeners();
        addMouseListeners();
        addKeyListeners();
    }
    
    private void addActionListeners() {
        administration.btnRegistrarCampo.addActionListener(this);
        administration.btnModificarCampoE.addActionListener(this);
        administration.btnEliminarCampoE.addActionListener(this);
        administration.btnCancelarCampo.addActionListener(this);
    }
    
    private void addMouseListeners() {
        administration.lblCampoE.addMouseListener(this);
        administration.CamposTable.addMouseListener(this);
    }
    
    private void addKeyListeners() {
        administration.txtFieldAngulo.addKeyListener(this);
        administration.txtFieldEDistance.addKeyListener(this);
        administration.txtFieldECharge.addKeyListener(this);
        administration.txtFieldEDirection.addKeyListener(this);
    }

    /**
     * Verifica si los campos están vacíos.
     *
     * @return true si alguno de los campos está vacío.
     */
    private boolean areFieldsEmpty() {
        return administration.txtFieldECharge.getText().isBlank()
                || administration.txtFieldEDistance.getText().isBlank()
                || administration.txtFieldAngulo.getText().isBlank()
                || administration.txtFieldEDirection.getText().isBlank();
    }

    /**
     * Limpia la tabla de campos eléctricos y los combobox relacionados.
     */
    private void cleanTable() {
        model = (DefaultTableModel) administration.CamposTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        administration.cmbCampoE.removeAllItems();
        administration.cmbCampoE2.removeAllItems();
        administration.cmbCampoE3.removeAllItems();
    }

    /**
     * Limpia los campos de texto de la interfaz.
     */
    private void cleanFields() {
        administration.txtFieldEID.setText("");
        administration.txtFieldECharge.setText("");
        administration.txtFieldEDirection.setText("");
        administration.txtFieldEDistance.setText("");
        administration.txtFieldAngulo.setText("");
        administration.txtResultadoCampoE.setText("");
    }

    /**
     * Maneja el registro de un nuevo campo eléctrico, validando los datos de
     * entrada.
     */
    private void registerField() {
        if (validateFieldInputs()) {
            fieldEActions.addFieldE(
                    Double.parseDouble(administration.txtFieldECharge.getText().trim()),
                    Double.parseDouble(administration.txtFieldEDistance.getText().trim()),
                    Double.parseDouble(administration.txtFieldAngulo.getText().trim()),
                    administration.txtFieldEDirection.getText().trim(),
                    Double.parseDouble(administration.txtResultadoCampoE.getText().trim())
            );
            refreshConsumeData();
            JOptionPane.showMessageDialog(null, "Campo eléctrico registrado con éxito.");
        }
    }

    /**
     * Valida los datos de entrada de los campos eléctricos.
     *
     * @return true si todos los campos son válidos.
     */
    private boolean validateFieldInputs() {
        if (!fieldEActions.isDoubleString(administration.txtFieldECharge.getText().trim())) {
            JOptionPane.showMessageDialog(null, "El campo de carga debe contener un número.");
            return false;
        }
        if (!fieldEActions.isDoubleString(administration.txtFieldEDistance.getText().trim())) {
            JOptionPane.showMessageDialog(null, "El campo de distancia debe contener un número.");
            return false;
        }
        if (!fieldEActions.isDoubleString(administration.txtFieldAngulo.getText().trim())) {
            JOptionPane.showMessageDialog(null, "El campo de ángulo debe contener un número.");
            return false;
        }
        return true;
    }
    
    private boolean validateFieldInputsTwo() {
        if (!fieldEActions.isDoubleString(administration.txtFieldECharge.getText().trim())) {
            
            return false;
        }
        if (!fieldEActions.isDoubleString(administration.txtFieldEDistance.getText().trim())) {
            
            return false;
        }
        return fieldEActions.isDoubleString(administration.txtFieldAngulo.getText().trim());
    }

    /**
     * Modifica un campo eléctrico existente.
     */
    private void modifyField() {
        if (validateFieldInputs()) {
            fieldEActions.modifyFieldE(Integer.parseInt(administration.txtFieldEID.getText().trim()),
                    Double.parseDouble(administration.txtFieldECharge.getText().trim()),
                    Double.parseDouble(administration.txtFieldEDistance.getText().trim()),
                    Double.parseDouble(administration.txtFieldAngulo.getText().trim()),
                    administration.txtFieldEDirection.getText().trim(),
                    Double.parseDouble(administration.txtResultadoCampoE.getText().trim())
            );
            refreshConsumeData();
            administration.btnRegistrarCampo.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Campo eléctrico modificado con éxito.");
        }
    }

    /**
     * Elimina un campo eléctrico.
     */
    private void deleteField() {
        if (fieldEActions.deleteFieldE(Integer.parseInt(administration.txtFieldEID.getText().trim()))) {
            refreshConsumeData();
            cleanFields();
            administration.btnRegistrarCampo.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Campo eléctrico eliminado con éxito.");
        }
    }

    /**
     * Refresca la tabla y los campos de la interfaz con los últimos datos de
     * los campos eléctricos.
     */
    private void refreshConsumeData() {
        cleanTable();
        cleanFields();
        loadFieldsE();
    }

    /**
     * Carga los campos eléctricos en la tabla y los combobox.
     */
    public void loadFieldsE() {
        Map<Integer, FieldE> fieldsE = fieldEActions.listFieldsE();
        model = (DefaultTableModel) administration.CamposTable.getModel();
        Object[] row = new Object[6];
        for (FieldE field : fieldsE.values()) {
            row[0] = field.getId();
            row[1] = field.getCargaQ();
            row[2] = field.getDireccion();
            row[3] = field.getDistanciaR();
            row[4] = field.getAnguloA();
            row[5] = field.getResult();
            model.addRow(row);
            administration.cmbCampoE.addItem(String.valueOf(row[5]));
            administration.cmbCampoE2.addItem(String.valueOf(row[5]));
            administration.cmbCampoE3.addItem(String.valueOf(row[5]));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnRegistrarCampo) {
            if (areFieldsEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            } else {
                registerField();
            }
        } else if (source == administration.btnModificarCampoE) {
            if (administration.CamposTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un campo de la tabla.");
            } else if (areFieldsEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            } else {
                modifyField();
            }
        } else if (source == administration.btnEliminarCampoE) {
            if (administration.CamposTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un campo de la tabla.");
            } else {
                deleteField();
            }
        } else if (source == administration.btnCancelarCampo) {
            refreshConsumeData();
            administration.btnRegistrarCampo.setEnabled(true);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.CamposTable) {
            int row = administration.CamposTable.rowAtPoint(e.getPoint());
            administration.txtFieldEID.setText(administration.CamposTable.getValueAt(row, 0).toString());
            administration.txtFieldECharge.setText(administration.CamposTable.getValueAt(row, 1).toString());
            administration.txtFieldEDirection.setText(administration.CamposTable.getValueAt(row, 2).toString());
            administration.txtFieldEDistance.setText(administration.CamposTable.getValueAt(row, 3).toString());
            administration.txtFieldAngulo.setText(administration.CamposTable.getValueAt(row, 4).toString());
            administration.txtResultadoCampoE.setText(administration.CamposTable.getValueAt(row, 5).toString());
            administration.btnRegistrarCampo.setEnabled(false);
        } else if (e.getSource() == administration.lblCampoE) {
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
        if (e.getSource() == administration.txtFieldECharge
                || e.getSource() == administration.txtFieldEDistance
                || e.getSource() == administration.txtFieldAngulo
                || e.getSource() == administration.txtFieldEDirection) {
            if (!areFieldsEmpty() && validateFieldInputsTwo()) {
                double k = 8.99e9; // Constante de Coulomb
                double cargaQ = Double.parseDouble(administration.txtFieldECharge.getText().trim());
                double distanciaR = Double.parseDouble(administration.txtFieldEDistance.getText().trim());
                double anguloA = Double.parseDouble(administration.txtFieldAngulo.getText().trim());

                // Convertir ángulo a radianes y calcular componentes del campo eléctrico
                double anguloRadianes = Math.toRadians(anguloA);
                double magnitud = (k * Math.abs(cargaQ)) / (distanciaR * distanciaR);
                double Ex = magnitud * Math.cos(anguloRadianes);
                double Ey = magnitud * Math.sin(anguloRadianes);
                double campoElectricoResultante = Math.sqrt(Ex * Ex + Ey * Ey);
                
                administration.txtResultadoCampoE.setText(String.valueOf(campoElectricoResultante));
            }
        }
    }
}
