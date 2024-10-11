package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * La clase FieldEActions gestiona las operaciones CRUD (Crear, Leer,
 * Actualizar, Eliminar) sobre los objetos FieldE. Implementa el patrón
 * Singleton para garantizar una única instancia.
 */
public class FieldEActions {

    // Singleton: instancia única de la clase
    private static class SingletonHelper {

        private static final FieldEActions INSTANCE = new FieldEActions();
    }

    public static FieldEActions getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Almacena los objetos FieldE usando un mapa donde la clave es su ID
    private final Map<Integer, FieldE> fieldsE = new HashMap<>();
    private int fieldECount = 0;

    // Expresión regular para verificar si una cadena es un double válido
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
     * Agrega un nuevo objeto FieldE al mapa.
     *
     * @param cargaQ Carga eléctrica en Coulombs
     * @param distanciaR Distancia al punto en metros
     * @param anguloA Ángulo en grados
     * @param direccion Dirección del campo
     * @param result Resultado del cálculo
     */
    public void addFieldE(double cargaQ, double distanciaR, double anguloA, String direccion, double result) {
        FieldE fieldE = new FieldE(fieldECount, cargaQ, distanciaR, anguloA, direccion, result);
        fieldsE.put(fieldECount, fieldE);
        fieldECount++;
    }

    /**
     * Modifica un objeto FieldE existente. Si el ID no existe, no se realiza la
     * modificación.
     *
     * @param id ID del objeto FieldE a modificar
     * @param cargaQ Nueva carga eléctrica
     * @param distanciaR Nueva distancia
     * @param anguloA Nuevo ángulo
     * @param direccion Nueva dirección
     * @param result Nuevo resultado
     */
    public void modifyFieldE(int id, double cargaQ, double distanciaR, double anguloA, String direccion, double result) {
        if (fieldsE.containsKey(id)) {
            FieldE fieldE = new FieldE(id, cargaQ, distanciaR, anguloA, direccion, result);
            fieldsE.put(id, fieldE);  // put sobreescribe si la clave ya existe
        }
    }

    /**
     * Elimina un objeto FieldE por su ID.
     *
     * @param id ID del objeto FieldE a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el ID
     */
    public boolean deleteFieldE(int id) {
        return fieldsE.remove(id) != null;
    }

    /**
     * Lista todos los objetos FieldE almacenados.
     *
     * @return Un mapa con todos los objetos FieldE.
     */
    public Map<Integer, FieldE> listFieldsE() {
        return new HashMap<>(fieldsE); // Devuelve una copia para evitar modificaciones externas
    }
}
