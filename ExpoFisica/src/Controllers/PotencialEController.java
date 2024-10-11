package Controllers;

import Models.PotencialE;
import Models.PotencialEActions;
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

public class PotencialEController implements ActionListener, MouseListener, KeyListener {

    private final Administration administration; // Referencia a la vista de administración
    private final PotencialEActions potencialEActions; // Referencia a las acciones de PotencialE
    private DefaultTableModel model; // Modelo de la tabla para manejar datos

    // Constructor que inicializa las referencias y agrega listeners
    public PotencialEController(Administration administration, PotencialEActions potencialEActions) {
        this.administration = administration;
        this.potencialEActions = potencialEActions;
        initializeListeners();
    }

    // Método para inicializar todos los listeners
    private void initializeListeners() {
        addActionListeners();
        addMouseListeners();
        addKeyListeners();
    }

    // Agrega ActionListeners a los botones de la interfaz
    private void addActionListeners() {
        administration.btnRegistrarPotencialE.addActionListener(this);
        administration.btnModificarPotencialE.addActionListener(this);
        administration.btnEliminarPotencialE.addActionListener(this);
        administration.btnCancelarPotencialE.addActionListener(this);
    }

    // Agrega MouseListeners a los componentes relevantes
    private void addMouseListeners() {
        administration.lblPotencialE.addMouseListener(this);
        administration.potencialETable.addMouseListener(this);
    }

    // Agrega KeyListener al campo de distancia
    private void addKeyListeners() {
        administration.txtPotencialEDistancia.addKeyListener(this);
    }

    // Verifica si hay campos vacíos en la interfaz
    private boolean areFieldsClean() {
        return administration.txtPotencialEDistancia.getText().isBlank()
                || administration.cmbCampoE3.getSelectedItem() == null; // Mejorar verificación de campo
    }

    // Limpia la tabla de potenciales
    private void cleanTable() {
        model = (DefaultTableModel) administration.potencialETable.getModel();
        model.setRowCount(0); // Más eficiente que eliminar fila por fila
    }

    // Limpia los campos de entrada de la interfaz
    private void cleanFields() {
        administration.txtPotencialEID.setText("");
        administration.txtPotencialEDistancia.setText("");
        administration.txtResultadoPotencialE.setText("");
    }

    // Actualiza la interfaz limpiando y cargando datos de potenciales
    private void refreshConsumeData() {
        cleanTable();
        cleanFields();
        loadPotencialesE();
    }

    // Carga todos los objetos PotencialE en la tabla
    public void loadPotencialesE() {
        Map<Integer, PotencialE> potenciales = potencialEActions.listPotenciales();
        model = (DefaultTableModel) administration.potencialETable.getModel();
        Object[] row;

        for (PotencialE value : potenciales.values()) { // Mejora en la iteración
            row = new Object[]{
                value.getId(),
                value.getCampoE(),
                value.getDistanciaD(),
                value.getResultado()
            };
            model.addRow(row);
        }
    }

    // Registra un nuevo PotencialE a partir de los datos de la interfaz
    private void registerPotencial() {
        String distanciaText = administration.txtPotencialEDistancia.getText().trim();
        String campoEText = administration.cmbCampoE3.getSelectedItem().toString().trim();

        if (!potencialEActions.isDoubleString(distanciaText)) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la distancia\nse deben ingresar datos numéricos.");
        } else if (campoEText.isBlank()) {
            JOptionPane.showMessageDialog(null, "No ha registrado campos electricos todavía.");
        } else {
            double campoE = Double.parseDouble(campoEText);
            double distancia = Double.parseDouble(distanciaText);
            double resultado = Double.parseDouble(administration.txtResultadoPotencialE.getText().trim());

            potencialEActions.addPotencialE(campoE, distancia, resultado);
            refreshConsumeData();
            JOptionPane.showMessageDialog(null, "Potencial Electrico registrado con éxito.");
        }
    }

    // Modifica un PotencialE existente
    private void modifyPotencial() {
        String distanciaText = administration.txtPotencialEDistancia.getText().trim();
        String campoEText = administration.cmbCampoE3.getSelectedItem().toString().trim();

        if (!potencialEActions.isDoubleString(distanciaText)) {
            JOptionPane.showMessageDialog(null, "Recuerda que en el campo correspondiente a la distancia\nse deben ingresar datos numéricos.");
        } else if (campoEText.isBlank()) {
            JOptionPane.showMessageDialog(null, "No ha registrado campos electricos todavía.");
        } else {
            int id = Integer.parseInt(administration.txtPotencialEID.getText());
            double campoE = Double.parseDouble(campoEText);
            double distancia = Double.parseDouble(distanciaText);
            double resultado = Double.parseDouble(administration.txtResultadoPotencialE.getText().trim());

            if (potencialEActions.modifyPotencialE(id, campoE, distancia, resultado)) {
                refreshConsumeData();
                administration.btnRegistrarPotencialE.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Potencial modificado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el potencial. Verifique el ID.");
            }
        }
    }

    // Elimina un PotencialE por su ID
    private void deletePotencial() {
        int id = Integer.parseInt(administration.txtPotencialEID.getText().trim());
        if (potencialEActions.deletePotencialE(id)) {
            refreshConsumeData();
            administration.btnRegistrarPotencialE.setEnabled(true); // Reinicia el estado del botón
            JOptionPane.showMessageDialog(null, "Potencial eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al eliminar el potencial. Verifique el ID.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == administration.btnRegistrarPotencialE) {
            if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos."); // Mejora de mensaje
            } else {
                registerPotencial();
            }
        } else if (source == administration.btnModificarPotencialE) {
            int row = administration.potencialETable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla."); // Mejora de mensaje
            } else if (areFieldsClean()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos."); // Mejora de mensaje
            } else {
                modifyPotencial();
            }
        } else if (source == administration.btnEliminarPotencialE) {
            int row = administration.potencialETable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla."); // Mejora de mensaje
            } else {
                deletePotencial();
            }
        } else if (source == administration.btnCancelarPotencialE) {
            refreshConsumeData();
            administration.btnRegistrarPotencialE.setEnabled(true); // Reinicia el estado del botón
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == administration.potencialETable) {
            int row = administration.potencialETable.rowAtPoint(e.getPoint());
            if (row != -1) {
                // Carga datos del potencial seleccionado en los campos
                administration.txtPotencialEID.setText(administration.potencialETable.getValueAt(row, 0).toString());
                administration.cmbCampoE3.setSelectedItem(administration.potencialETable.getValueAt(row, 1).toString());
                administration.txtPotencialEDistancia.setText(administration.potencialETable.getValueAt(row, 2).toString());
                administration.txtResultadoPotencialE.setText(administration.potencialETable.getValueAt(row, 3).toString());
                administration.btnRegistrarPotencialE.setEnabled(false); // Deshabilita el botón de registro
            }
        } else if (e.getSource() == administration.lblPotencialE) {
            administration.jTabbedPanePanels.setSelectedIndex(5); // Cambia a la pestaña de gestión de productos
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
        // Calcula el resultado del potencial basado en la distancia y el campo eléctrico
        if (e.getSource() == administration.txtPotencialEDistancia) {
            if (!administration.txtPotencialEDistancia.getText().isBlank()
                    && administration.cmbCampoE3.getSelectedItem() != null) {
                double distancia = Double.parseDouble(administration.txtPotencialEDistancia.getText().trim());
                double campoE = Double.parseDouble(administration.cmbCampoE3.getSelectedItem().toString().trim());
                double result = distancia * campoE;
                administration.txtResultadoPotencialE.setText(String.valueOf(result));
            }
        }
    }
}
