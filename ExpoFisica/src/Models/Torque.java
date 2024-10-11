package Models;

/**
 * La clase Torque representa el cálculo de un torque basado en el campo
 * eléctrico, la carga, la distancia, y el ángulo.
 */
public class Torque {

    private int id;             // Identificador único del torque
    private double campoE;      // Campo eléctrico en N/C
    private double cargaQ;      // Carga eléctrica en Coulombs
    private double distanciaR;  // Distancia en metros
    private double anguloA;     // Ángulo en grados
    private double resultado;   // Resultado del torque calculado

    /**
     * Constructor vacío de Torque. Útil cuando se desea inicializar un objeto
     * sin datos iniciales.
     */
    public Torque() {
        // Constructor vacío
    }

    /**
     * Constructor completo de Torque.
     *
     * @param id Identificador único del torque.
     * @param campoE Campo eléctrico en N/C.
     * @param cargaQ Carga eléctrica en Coulombs.
     * @param distanciaR Distancia en metros.
     * @param anguloA Ángulo en grados.
     * @param resultado Resultado del torque calculado.
     */
    public Torque(int id, double campoE, double cargaQ, double distanciaR, double anguloA, double resultado) {
        this.id = id;
        this.campoE = campoE;
        this.cargaQ = cargaQ;
        this.distanciaR = distanciaR;
        this.anguloA = anguloA;
        this.resultado = resultado;
    }

    /**
     * Obtiene el resultado del torque calculado.
     *
     * @return Resultado del torque.
     */
    public double getResultado() {
        return resultado;
    }

    /**
     * Establece el resultado del torque calculado.
     *
     * @param resultado Nuevo valor del resultado del torque.
     */
    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    /**
     * Obtiene el identificador único del torque.
     *
     * @return id del torque.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del torque.
     *
     * @param id Nuevo id del torque.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el valor del campo eléctrico.
     *
     * @return Valor del campo eléctrico en N/C.
     */
    public double getCampoE() {
        return campoE;
    }

    /**
     * Establece el valor del campo eléctrico.
     *
     * @param campoE Nuevo valor del campo eléctrico en N/C.
     */
    public void setCampoE(double campoE) {
        this.campoE = campoE;
    }

    /**
     * Obtiene el valor de la carga eléctrica.
     *
     * @return Valor de la carga eléctrica en Coulombs.
     */
    public double getCargaQ() {
        return cargaQ;
    }

    /**
     * Establece el valor de la carga eléctrica.
     *
     * @param cargaQ Nueva carga eléctrica en Coulombs.
     */
    public void setCargaQ(double cargaQ) {
        this.cargaQ = cargaQ;
    }

    /**
     * Obtiene la distancia en metros.
     *
     * @return Distancia en metros.
     */
    public double getDistanciaR() {
        return distanciaR;
    }

    /**
     * Establece la distancia en metros.
     *
     * @param distanciaR Nueva distancia en metros.
     */
    public void setDistanciaR(double distanciaR) {
        this.distanciaR = distanciaR;
    }

    /**
     * Obtiene el ángulo en grados.
     *
     * @return Ángulo en grados.
     */
    public double getAnguloA() {
        return anguloA;
    }

    /**
     * Establece el ángulo en grados.
     *
     * @param anguloA Nuevo ángulo en grados.
     */
    public void setAnguloA(double anguloA) {
        this.anguloA = anguloA;
    }
}
