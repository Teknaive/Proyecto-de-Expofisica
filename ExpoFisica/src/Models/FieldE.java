package Models;

/**
 * La clase FieldE representa un campo eléctrico con propiedades como carga,
 * distancia, ángulo y dirección. Permite almacenar los datos de un campo
 * eléctrico y calcular su resultado.
 */
public class FieldE {

    private int id;          // ID del campo
    private double cargaQ;    // Carga eléctrica en Coulombs
    private double distanciaR; // Distancia en metros
    private double anguloA;   // Ángulo en grados
    private String direccion; // Dirección del campo
    private double result;    // Resultado del cálculo

    /**
     * Constructor vacío de FieldE. Útil cuando se desea inicializar un objeto
     * sin datos iniciales.
     */
    public FieldE() {
        // Constructor vacío
    }

    /**
     * Constructor completo de FieldE.
     *
     * @param id Identificador único
     * @param cargaQ Carga eléctrica
     * @param distanciaR Distancia al punto
     * @param anguloA Ángulo del campo
     * @param direccion Dirección del campo
     * @param result Resultado del cálculo del campo eléctrico
     */
    public FieldE(int id, double cargaQ, double distanciaR, double anguloA, String direccion, double result) {
        this.id = id;
        this.cargaQ = cargaQ;
        this.distanciaR = distanciaR;
        this.anguloA = anguloA;
        this.direccion = direccion;
        this.result = result;
    }

    /**
     * Obtiene el ID del campo.
     *
     * @return id del campo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del campo.
     *
     * @param id nuevo id del campo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la carga eléctrica.
     *
     * @return carga eléctrica en Coulombs.
     */
    public double getCargaQ() {
        return cargaQ;
    }

    /**
     * Establece la carga eléctrica.
     *
     * @param cargaQ nueva carga eléctrica.
     */
    public void setCargaQ(double cargaQ) {
        this.cargaQ = cargaQ;
    }

    /**
     * Obtiene la distancia al punto.
     *
     * @return distancia en metros.
     */
    public double getDistanciaR() {
        return distanciaR;
    }

    /**
     * Establece la distancia al punto.
     *
     * @param distanciaR nueva distancia en metros.
     */
    public void setDistanciaR(double distanciaR) {
        this.distanciaR = distanciaR;
    }

    /**
     * Obtiene el ángulo del campo.
     *
     * @return ángulo en grados.
     */
    public double getAnguloA() {
        return anguloA;
    }

    /**
     * Establece el ángulo del campo.
     *
     * @param anguloA nuevo ángulo en grados.
     */
    public void setAnguloA(double anguloA) {
        this.anguloA = anguloA;
    }

    /**
     * Obtiene la dirección del campo.
     *
     * @return dirección como cadena.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del campo.
     *
     * @param direccion nueva dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el resultado del cálculo.
     *
     * @return resultado en double.
     */
    public double getResult() {
        return result;
    }

    /**
     * Establece el resultado del cálculo.
     *
     * @param result nuevo resultado.
     */
    public void setResult(double result) {
        this.result = result;
    }
}
