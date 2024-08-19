package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElectricConsumeActions {

    private static ElectricConsumeActions instance;

    private Map<Integer, ElectricConsume> electricConsumes = new HashMap<>();

    private Integer electricConsumeCount = 0;

    public static synchronized ElectricConsumeActions getInstance() {
        if (instance == null) {
            instance = new ElectricConsumeActions();
        }
        return instance;
    }

    private ElectricConsume createElectricConsume(Integer ID, String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, Double result, String user) {
        return new ElectricConsume(ID, electricAppliance, electricPower, deviceTime, electricityTariff, result, user);
    }

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

    public void addElectricConsume(String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, String user) {
        Double result = electricPower * deviceTime * electricityTariff;
        ElectricConsume electricConsume = createElectricConsume(electricConsumeCount, electricAppliance, electricPower, deviceTime, electricityTariff, result, user);
        electricConsumes.put(electricConsumeCount, electricConsume);
        electricConsumeCount++;
    }

    public boolean modifyElectricConsume(Integer ID, String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, String user) {
        if (electricConsumes.containsKey(ID)) {
            ElectricConsume electricConsume = createElectricConsume(ID, electricAppliance, electricPower, deviceTime, electricityTariff, electricPower * deviceTime * electricityTariff, user);
            electricConsumes.replace(ID, electricConsume);
            return true;
        }
        return false;
    }

    public boolean deleteElectricConsume(Integer ID) {
        if (electricConsumes.containsKey(ID)) {
            electricConsumes.remove(ID);
            return true;
        }
        return false;
    }

    public Map<Integer, ElectricConsume> listElectricConsume(String electricAppliance) {
        if (electricAppliance.isBlank()) {
            return electricConsumes;
        } else {
            Map<Integer, ElectricConsume> filteredElectricConsume = new HashMap<>();
            for (ElectricConsume electricConsume : electricConsumes.values()) {
                if (electricConsume.getElectricAppliance().equalsIgnoreCase(electricAppliance)) {
                    filteredElectricConsume.put(electricConsume.getID(), electricConsume);
                }
            }
            return filteredElectricConsume.isEmpty() ? electricConsumes : filteredElectricConsume;
        }
    }

}
