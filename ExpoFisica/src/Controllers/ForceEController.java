package Controllers;

import Models.ForceActions;
import Models.ForceE;
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
 * Controlador para gestionar las fuerzas eléctricas y la interfaz de usuario en
 * la vista de administración. Implementa ActionListener, MouseListener y
 * KeyListener para manejar eventos.
 */
public class ForceEController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration;
    private final ForceActions forceActions;
    private DefaultTableModel model;

    /**
     * Constructor para inicializar el controlador de fuerzas eléctricas.
     *
     * @param administration La vista de administración.
     * @param forceActions Las acciones relacionadas con ForceE.
     */
    public ForceEController(Administration administration, ForceActions forceActions) {
        this.administration = administration;
        this.forceActions = forceActions;
        addActionListeners();
        addMouseListeners();
        addKeyListeners();
    }

    // Métodos para agregar listeners
    private void addActionListeners() {
        administration.btnRegistrarFuerza.addActionListener(this);
        administration.btnModificarFuerza.addActionListener(this);
        administration.btnEliminarFuerza.addActionListener(this);
        administration.btnCancelarFuerza.addActionListener(this);
    }

    private void addMouseListeners() {
        administration.lblLeyCoulomb.addMouseListener(this);
        administration.fuerzasTable.addMouseListener(this);
    }

    private void addKeyListeners() {
        administration.txtCargaLeyCoulomb.addKeyListener(this);
    }

    /**
     * Verifica si los campos requeridos están vacíos.
     *
     * @return true si alguno de los campos está vacío.
     */
    private boolean areFieldsEmpty() {
        return administration.txtCargaLeyCoulomb.getText().isBlank()
                || administration.cmbCampoE.getSelectedItem() == null;
    }

    /**
     * Limpia la tabla de fuerzas eléctricas.
     */
    private void cleanTable() {
        model = (DefaultTableModel) administration.fuerzasTable.getModel();
        model.setRowCount(0);  // Limpia todas las filas
    }

    /**
     * Limpia los campos de entrada de la vista.
     */
    private void cleanFields() {
        administration.txtFuerzaID.setText("");
        administration.txtCargaLeyCoulomb.setText("");
        administration.txtResultadoLeyCoulomb.setText("");
    }

    /**
     * Actualiza la tabla y los campos de la interfaz.
     */
    private void refreshData() {
        cleanTable();
        cleanFields();
        loadForcesE();
    }

    /**
     * Carga las fuerzas eléctricas en la tabla.
     */
    public void loadForcesE() {
        Map<Integer, ForceE> forces = forceActions.listForceE();
        model = (DefaultTableModel) administration.fuerzasTable.getModel();
        Object[] row = new Object[4];
        for (ForceE force : forces.values()) {
            row[0] = force.getId();
            row[1] = force.getCargaQ();
            row[2] = force.getCampoE();
            row[3] = force.getResultado();
            model.addRow(row);
        }
    }

    /**
     * Valida la entrada de carga y campo eléctrico antes de registrar o
     * modificar.
     *
     * @return true si la validación es exitosa.
     */
    private boolean validateInputs() {
        try {
            // Verifica si los valores son numéricos válidos
            if (!forceActions.isDoubleString(administration.txtCargaLeyCoulomb.getText().trim())) {
                throw new NumberFormatException("El valor de carga debe ser un número.");
            }
            if (administration.cmbCampoE.getSelectedItem() == null || administration.cmbCampoE.getSelectedItem().toString().isBlank()) {
                throw new IllegalArgumentException("Debe seleccionar un campo eléctrico.");
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    /**
     * Registra una nueva fuerza eléctrica.
     */
    private void registerForce() {
        if (validateInputs()) {
            double campoE = Double.parseDouble(administration.cmbCampoE.getSelectedItem().toString().trim());
            double cargaQ = Double.parseDouble(administration.txtCargaLeyCoulomb.getText().trim());
            double resultado = campoE * cargaQ;
            administration.txtResultadoLeyCoulomb.setText(String.valueOf(resultado));

            forceActions.addForceE(campoE, cargaQ, resultado);
            refreshData();
            JOptionPane.showMessageDialog(null, "Fuerza registrada con éxito.");
        }
    }

    /**
     * Modifica una fuerza eléctrica existente.
     */
    private void modifyForce() {
        if (validateInputs()) {
            int id = Integer.parseInt(administration.txtFuerzaID.getText().trim());
            double campoE = Double.parseDouble(administration.cmbCampoE.getSelectedItem().toString().trim());
            double cargaQ = Double.parseDouble(administration.txtCargaLeyCoulomb.getText().trim());
            double resultado = campoE * cargaQ;
            administration.txtResultadoLeyCoulomb.setText(String.valueOf(resultado));

            forceActions.modifyForceE(id, campoE, cargaQ, resultado);
            refreshData();
            JOptionPane.showMessageDialog(null, "Fuerza modificada con éxito.");
        }
    }

    /**
     * Elimina una fuerza eléctrica.
     */
    private void deleteForce() {
        int id = Integer.parseInt(administration.txtFuerzaID.getText().trim());
        if (forceActions.deleteForceE(id)) {
            refreshData();
            JOptionPane.showMessageDialog(null, "Fuerza eliminada con éxito.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == administration.btnRegistrarFuerza) {
            if (areFieldsEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            } else {
                registerForce();
            }
        } else if (source == administration.btnModificarFuerza) {
            if (administration.fuerzasTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla.");
            } else if (areFieldsEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            } else {
                modifyForce();
            }
        } else if (source == administration.btnEliminarFuerza) {
            if (administration.fuerzasTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla.");
            } else {
                deleteForce();
            }
        } else if (source == administration.btnCancelarFuerza) {
            refreshData();
            administration.btnRegistrarFuerza.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.fuerzasTable) {
            int row = administration.fuerzasTable.rowAtPoint(e.getPoint());
            administration.txtFuerzaID.setText(administration.fuerzasTable.getValueAt(row, 0).toString());
            administration.txtCargaLeyCoulomb.setText(administration.fuerzasTable.getValueAt(row, 1).toString());
            administration.cmbCampoE.setSelectedItem(administration.fuerzasTable.getValueAt(row, 2).toString());
            administration.txtResultadoLeyCoulomb.setText(administration.fuerzasTable.getValueAt(row, 3).toString());
            administration.btnRegistrarFuerza.setEnabled(false);
        } else if (e.getSource() == administration.lblLeyCoulomb) {
            administration.jTabbedPanePanels.setSelectedIndex(2);
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
        if (e.getSource() == administration.txtCargaLeyCoulomb && !areFieldsEmpty()) {
            try {
                double campoE = Double.parseDouble(administration.cmbCampoE.getSelectedItem().toString().trim());
                double cargaQ = Double.parseDouble(administration.txtCargaLeyCoulomb.getText().trim());
                double result = campoE * cargaQ;
                administration.txtResultadoLeyCoulomb.setText(String.valueOf(result));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido.");
            }
        }
    }
}
