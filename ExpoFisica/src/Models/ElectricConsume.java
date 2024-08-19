package Models;

public class ElectricConsume {

    private Integer ID;
    private String electricAppliance;
    private Double electricPower;
    private Double deviceTime;
    private Double electricityTariff;
    private Double result;
    private String user;

    public ElectricConsume() {
    }

    public ElectricConsume(Integer ID, String electricAppliance, Double electricPower, Double deviceTime, Double electricityTariff, Double result, String user) {
        this.ID = ID;
        this.electricAppliance = electricAppliance;
        this.electricPower = electricPower;
        this.deviceTime = deviceTime;
        this.electricityTariff = electricityTariff;
        this.result = result;
        this.user = user;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

}
