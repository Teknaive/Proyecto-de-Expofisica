package Models;

public class FieldE {

    private Integer ID;
    private double cargaQ;
    private double distanciaR;
    private double anguloA;
    private String direccion;
    private double result;

    public FieldE() {
    }

    public FieldE(Integer ID, double cargaQ, double distanciaR, double anguloA, String direccion, double result) {
        this.ID = ID;
        this.cargaQ = cargaQ;
        this.distanciaR = distanciaR;
        this.anguloA = anguloA;
        this.direccion = direccion;
        this.result = result;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public double getCargaQ() {
        return cargaQ;
    }

    public void setCargaQ(double cargaQ) {
        this.cargaQ = cargaQ;
    }

    public double getDistanciaR() {
        return distanciaR;
    }

    public void setDistanciaR(double distanciaR) {
        this.distanciaR = distanciaR;
    }

    public double getAnguloA() {
        return anguloA;
    }

    public void setAnguloA(double anguloA) {
        this.anguloA = anguloA;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    

    

}
