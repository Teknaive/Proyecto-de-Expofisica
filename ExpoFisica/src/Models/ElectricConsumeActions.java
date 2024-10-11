package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que gestiona las acciones relacionadas con el consumo eléctrico.
 */
public class ElectricConsumeActions {

    // Singleton instance
    private static ElectricConsumeActions instance;

    // Mapa para almacenar los consumos eléctricos
    private final Map<Integer, ElectricConsume> electricConsumes = new HashMap<>();

    // Contador para asignar IDs únicos
    private Integer electricConsumeCount = 0;

    // Método para obtener la instancia única (Singleton)
    public static synchronized ElectricConsumeActions getInstance() {
        if (instance == null) {
            instance = new ElectricConsumeActions();
        }
        return instance;
    }

    // Constructor privado para el patrón Singleton
    private ElectricConsumeActions() {
    }

    // Método para crear una instancia de ElectricConsume
    private ElectricConsume createElectricConsume(Integer id, String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, String user) {
        return new ElectricConsume(id, electricAppliance, electricPower, deviceTime, electricityTariff, user);
    }

    // Verifica si la cadena de entrada es un número decimal válido
    public boolean isDoubleString(String input) {
        String numericRegex = "^[0-9]+(\\.[0-9]+)?$"; // Expresión regular para números decimales
        Pattern pattern = Pattern.compile(numericRegex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches(); // Devuelve true si la entrada es un número decimal válido
    }

    // Valida y formatea el nombre: capitaliza la primera letra, el resto en minúsculas
    public String nameAdditionalValidation(String value) {
        if (value == null || value.isBlank()) {
            return value; // Devuelve tal cual si es nulo o vacío
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // Añade un nuevo consumo eléctrico
    public void addElectricConsume(String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, String user) {
        ElectricConsume electricConsume = createElectricConsume(electricConsumeCount, electricAppliance, electricPower, deviceTime, electricityTariff, user);
        electricConsumes.put(electricConsumeCount, electricConsume);
        electricConsumeCount++;
    }

    // Modifica un consumo eléctrico existente
    public boolean modifyElectricConsume(Integer id, String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, String user) {
        if (electricConsumes.containsKey(id)) {
            ElectricConsume electricConsume = createElectricConsume(id, electricAppliance, electricPower, deviceTime, electricityTariff, user);
            electricConsumes.replace(id, electricConsume);
            return true;
        }
        return false;
    }

    // Elimina un consumo eléctrico existente
    public boolean deleteElectricConsume(Integer id) {
        if (electricConsumes.containsKey(id)) {
            electricConsumes.remove(id);
            return true;
        }
        return false;
    }

    // Lista los consumos eléctricos, filtrando por aparato si se proporciona
    public Map<Integer, ElectricConsume> listElectricConsume(String electricAppliance) {
        if (electricAppliance == null || electricAppliance.isBlank()) {
            return electricConsumes;
        } else {
            Map<Integer, ElectricConsume> filteredElectricConsume = new HashMap<>();
            for (ElectricConsume electricConsume : electricConsumes.values()) {
                if (electricConsume.getElectricAppliance().equalsIgnoreCase(electricAppliance)) {
                    filteredElectricConsume.put(electricConsume.getId(), electricConsume);
                }
            }
            return filteredElectricConsume.isEmpty() ? electricConsumes : filteredElectricConsume;
        }
    }
}
