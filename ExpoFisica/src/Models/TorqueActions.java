package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * La clase TorqueActions gestiona las operaciones CRUD sobre los objetos
 * Torque, implementando un patrón Singleton para garantizar una única
 * instancia.
 */
public class TorqueActions {

    // Singleton: instancia única de la clase
    private static class SingletonHelper {

        private static final TorqueActions INSTANCE = new TorqueActions();
    }

    public static TorqueActions getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Mapa que almacena los torques, donde la clave es el ID
    private final Map<Integer, Torque> torques = new HashMap<>();
    private int torqueCount = 0; // Contador de los torques creados

    // Expresión regular precompilada para validar si una cadena es un número decimal
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

    /**
     * Verifica si una cadena representa un número double válido.
     *
     * @param input Cadena a verificar.
     * @return true si es un número double válido, false en caso contrario.
     */
    public boolean isDoubleString(String input) {
        return DOUBLE_PATTERN.matcher(input).matches();
    }

    /**
     * Agrega un nuevo torque al mapa.
     *
     * @param campoE Campo eléctrico en N/C.
     * @param cargaQ Carga eléctrica en Coulombs.
     * @param distanciaR Distancia en metros.
     * @param anguloA Ángulo en grados.
     * @param resultado Resultado del torque calculado.
     */
    public void addTorque(double campoE, double cargaQ, double distanciaR, double anguloA, double resultado) {
        Torque torque = new Torque(torqueCount, campoE, cargaQ, distanciaR, anguloA, resultado);
        torques.put(torqueCount, torque);
        torqueCount++;
    }

    /**
     * Modifica un torque existente. Si el ID no existe, no se realiza la
     * modificación.
     *
     * @param id ID del torque a modificar.
     * @param campoE Nuevo valor del campo eléctrico.
     * @param cargaQ Nueva carga eléctrica.
     * @param distanciaR Nueva distancia en metros.
     * @param anguloA Nuevo ángulo en grados.
     * @param resultado Nuevo resultado del torque.
     */
    public void modifyTorque(int id, double campoE, double cargaQ, double distanciaR, double anguloA, double resultado) {
        if (torques.containsKey(id)) {
            Torque torque = new Torque(id, campoE, cargaQ, distanciaR, anguloA, resultado);
            torques.put(id, torque);  // put sobrescribe si la clave ya existe
        }
    }

    /**
     * Elimina un torque del mapa.
     *
     * @param id ID del torque a eliminar.
     * @return true si el torque fue eliminado, false si no se encontró el ID.
     */
    public boolean deleteTorque(int id) {
        return torques.remove(id) != null;
    }

    /**
     * Lista todos los torques almacenados.
     *
     * @return Un mapa que contiene todos los torques.
     */
    public Map<Integer, Torque> listTorques() {
        return new HashMap<>(torques);  // Retorna una copia del mapa para evitar modificaciones externas
    }
}
