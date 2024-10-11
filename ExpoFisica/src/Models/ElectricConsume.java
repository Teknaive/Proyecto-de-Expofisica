package Models;

/**
 * Clase que representa el consumo eléctrico de un aparato.
 */
public class ElectricConsume {

    // Atributos privados para encapsulamiento
    private Integer id;
    private String electricAppliance; // Aparato que consume electricidad
    private Double electricPower; // Potencia en vatios
    private Double deviceTime; // Tiempo de uso en horas
    private Double electricityTariff; // Tarifa eléctrica en moneda local por kWh
    private Double result; // Resultado del cálculo de consumo
    private String user; // Usuario asociado

    // Constructor por defecto
    public ElectricConsume() {
    }

    // Constructor con parámetros
    public ElectricConsume(Integer id, String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, String user) {
        this.id = id;
        this.electricAppliance = electricAppliance;
        this.electricPower = electricPower;
        this.deviceTime = deviceTime;
        this.electricityTariff = electricityTariff;
        this.user = user;
        this.result = calculateResult(); // Calcula el resultado automáticamente
    }

    // Métodos getter y setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElectricAppliance() {
        return electricAppliance;
    }

    public void setElectricAppliance(String electricAppliance) {
        this.electricAppliance = electricAppliance;
    }

    public Double getElectricPower() {
        return electricPower;
    }

    public void setElectricPower(Double electricPower) {
        this.electricPower = electricPower;
    }

    public Double getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(Double deviceTime) {
        this.deviceTime = deviceTime;
    }

    public Double getElectricityTariff() {
        return electricityTariff;
    }

    public void setElectricityTariff(Double electricityTariff) {
        this.electricityTariff = electricityTariff;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Método para calcular el consumo eléctrico
    private Double calculateResult() {
        if (electricPower != null && deviceTime != null && electricityTariff != null) {
            return electricPower * deviceTime * electricityTariff / 1000; // Convierte vatios a kilovatios
        }
        return 0.0;
    }
}
