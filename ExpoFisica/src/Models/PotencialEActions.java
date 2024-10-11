package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase singleton para gestionar acciones relacionadas con PotencialE.
 */
public class PotencialEActions {

    // Instancia única de la clase (patrón Singleton)
    private static PotencialEActions instance;

    // Método para obtener la instancia única de la clase
    public static synchronized PotencialEActions getInstance() {
        if (instance == null) {
            instance = new PotencialEActions();
        }
        return instance;
    }

    // Mapa para almacenar los objetos PotencialE
    private Map<Integer, PotencialE> potenciales = new HashMap<>();

    // Contador para asignar IDs únicos a los objetos PotencialE
    private Integer potencialECount = 0;

    // Expresión regular para validar si una cadena es un número decimal
    private static final String NUMERIC_REGEX = "^[0-9]+(\\.[0-9]+)?$";

    // Constructor privado para evitar instanciación externa (patrón Singleton)
    private PotencialEActions() {
    }

    /**
     * Verifica si la cadena de entrada es un número decimal válido.
     *
     * @param input Cadena de entrada a verificar.
     * @return true si la cadena es un número decimal válido, false en caso
     * contrario.
     */
    public boolean isDoubleString(String input) {
        Pattern pattern = Pattern.compile(NUMERIC_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Crea un nuevo objeto PotencialE.
     *
     * @param id ID del objeto.
     * @param campoE Valor del campo eléctrico.
     * @param distanciaD Distancia.
     * @param resultado Resultado del cálculo.
     * @return Nuevo objeto PotencialE.
     */
    private PotencialE createPotencial(Integer id, double campoE, double distanciaD, double resultado) {
        return new PotencialE(id, campoE, distanciaD, resultado);
    }

    /**
     * Añade un nuevo objeto PotencialE al mapa.
     *
     * @param campoE Valor del campo eléctrico.
     * @param distanciaD Distancia.
     * @param resultado Resultado del cálculo.
     */
    public void addPotencialE(double campoE, double distanciaD, double resultado) {
        PotencialE potencialE = createPotencial(potencialECount, campoE, distanciaD, resultado);
        potenciales.put(potencialECount, potencialE);
        potencialECount++;
    }

    /**
     * Modifica un objeto PotencialE existente en el mapa.
     *
     * @param id ID del objeto a modificar.
     * @param campoE Nuevo valor del campo eléctrico.
     * @param distanciaD Nueva distancia.
     * @param resultado Nuevo resultado del cálculo.
     * @return true si el objeto fue modificado, false en caso contrario.
     */
    public boolean modifyPotencialE(Integer id, double campoE, double distanciaD, double resultado) {
        if (potenciales.containsKey(id)) {
            PotencialE potencialE = createPotencial(id, campoE, distanciaD, resultado);
            potenciales.replace(id, potencialE);
            return true;
        }
        return false;
    }

    /**
     * Elimina un objeto PotencialE del mapa.
     *
     * @param id ID del objeto a eliminar.
     * @return true si el objeto fue eliminado, false en caso contrario.
     */
    public boolean deletePotencialE(Integer id) {
        if (potenciales.containsKey(id)) {
            potenciales.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Lista todos los objetos PotencialE almacenados en el mapa.
     *
     * @return Mapa de objetos PotencialE.
     */
    public Map<Integer, PotencialE> listPotenciales() {
        return potenciales;
    }
}
