package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * La clase ForceActions gestiona las operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar) sobre los objetos ForceE. Implementa el patrón Singleton para
 * garantizar una única instancia.
 */
public class ForceActions {

    // Singleton: instancia única de la clase
    private static class SingletonHelper {

        private static final ForceActions INSTANCE = new ForceActions();
    }

    public static ForceActions getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Mapa para almacenar las fuerzas eléctricas con sus IDs
    private final Map<Integer, ForceE> forcesE = new HashMap<>();
    private int forceECount = 0; // Contador de las fuerzas creadas

    // Expresión regular precompilada para verificar si una cadena es un número double válido
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

    /**
     * Verifica si una cadena representa un número double válido.
     *
     * @param input Cadena a verificar
     * @return true si es un número double válido, false en caso contrario
     */
    public boolean isDoubleString(String input) {
        return DOUBLE_PATTERN.matcher(input).matches();
    }

    /**
     * Agrega una nueva fuerza eléctrica al mapa.
     *
     * @param campoE Campo eléctrico en N/C.
     * @param cargaQ Carga eléctrica en Coulombs.
     * @param resultado Resultado de la fuerza calculada.
     */
    public void addForceE(double campoE, double cargaQ, double resultado) {
        ForceE forceE = new ForceE(forceECount, campoE, cargaQ, resultado);
        forcesE.put(forceECount, forceE);
        forceECount++;
    }

    /**
     * Modifica una fuerza eléctrica existente. Si el ID no existe, no se
     * realiza la modificación.
     *
     * @param id ID de la fuerza a modificar.
     * @param campoE Nuevo valor del campo eléctrico.
     * @param cargaQ Nueva carga eléctrica.
     * @param resultado Nuevo resultado de la fuerza.
     */
    public void modifyForceE(int id, double campoE, double cargaQ, double resultado) {
        if (forcesE.containsKey(id)) {
            ForceE forceE = new ForceE(id, campoE, cargaQ, resultado);
            forcesE.put(id, forceE);  // put sobrescribe si la clave ya existe
        }
    }

    /**
     * Elimina una fuerza eléctrica del mapa.
     *
     * @param id ID de la fuerza a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró el ID.
     */
    public boolean deleteForceE(int id) {
        return forcesE.remove(id) != null;
    }

    /**
     * Lista todas las fuerzas eléctricas almacenadas.
     *
     * @return Un mapa con todas las fuerzas eléctricas.
     */
    public Map<Integer, ForceE> listForceE() {
        return new HashMap<>(forcesE); // Devuelve una copia para evitar modificaciones externas
    }
}
