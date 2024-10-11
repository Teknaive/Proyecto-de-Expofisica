package Models;

/**
 * La clase ForceE representa la fuerza eléctrica en base a un campo eléctrico y
 * una carga.
 */
public class ForceE {

    private int id;            // Identificador único de la fuerza
    private double campoE;     // Campo eléctrico en N/C
    private double cargaQ;     // Carga eléctrica en Coulombs
    private double resultado;  // Resultado del cálculo de la fuerza

    /**
     * Constructor vacío de ForceE. Útil cuando se desea inicializar un objeto
     * sin datos iniciales.
     */
    public ForceE() {
        // Constructor vacío
    }

    /**
     * Constructor completo de ForceE.
     *
     * @param id Identificador único de la fuerza.
     * @param campoE Valor del campo eléctrico en N/C.
     * @param cargaQ Valor de la carga eléctrica en Coulombs.
     * @param resultado Resultado de la fuerza calculada.
     */
    public ForceE(int id, double campoE, double cargaQ, double resultado) {
        this.id = id;
        this.campoE = campoE;
        this.cargaQ = cargaQ;
        this.resultado = resultado;
    }

    /**
     * Obtiene el resultado de la fuerza calculada.
     *
     * @return Resultado de la fuerza.
     */
    public double getResultado() {
        return resultado;
    }

    /**
     * Establece el resultado de la fuerza.
     *
     * @param resultado Nuevo valor del resultado.
     */
    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    /**
     * Obtiene el identificador único de la fuerza.
     *
     * @return id de la fuerza.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único de la fuerza.
     *
     * @param id Nuevo id de la fuerza.
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
}
