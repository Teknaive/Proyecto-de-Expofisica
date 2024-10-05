package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldEActions {

    private static FieldEActions instance;

    public static synchronized FieldEActions getInstance() {
        if (instance == null) {
            instance = new FieldEActions();
        }
        return instance;
    }

    private Map<Integer, FieldE> fieldsE = new HashMap<>();

    private Integer fieldECount = 0;

    // Check if the input string is a valid double
    public boolean isDoubleString(String input) {
        String numericRegex = "^[0-9]+(\\.[0-9]+)?$"; // Regular expression for double string
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Return true if input is a valid double
    }

    // Validate and format name: capitalize the first letter, rest in lowercase
    public String nameAdditionalValidation(String value) {
        if (value.isBlank()) {
            return value; // Return as is if null or empty
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    private FieldE createFieldE(Integer ID, double cargaQ, double distanciaR, double anguloA, String direccion, double result) {
        return new FieldE(ID, cargaQ, distanciaR, anguloA, direccion, result);
    }

    public void addFieldE(double cargaQ, double distanciaR, double anguloA, String direccion) {
        double k = 8.99e9; // Constante de Coulomb en N·m²/C²
        double result;

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

        FieldE fieldE = createFieldE(fieldECount, cargaQ, distanciaR, anguloA, direccion, result);
        fieldsE.put(fieldECount, fieldE);
        fieldECount++;
    }

    public void modifyFieldE(Integer ID, double cargaQ, double distanciaR, double anguloA, String direccion) {
        if (fieldsE.containsKey(ID)) {
            double k = 8.99e9; // Constante de Coulomb en N·m²/C²
            double result;

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

            FieldE fieldE = createFieldE(fieldECount, cargaQ, distanciaR, anguloA, direccion, result);
            fieldsE.replace(ID, fieldE);
        }

    }

    public boolean deleteFieldE(Integer ID) {
        if (fieldsE.containsKey(ID)) {
            fieldsE.remove(ID);
            return true;
        }
        return false;
    }

    public Map<Integer, FieldE> listFieldsE() {
        return fieldsE;
    }
}
