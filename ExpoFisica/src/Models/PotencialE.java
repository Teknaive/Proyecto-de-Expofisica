package Models;

/**
 * Clase que representa el potencial eléctrico. Incluye atributos para el ID,
 * campo eléctrico, distancia y resultado.
 */
public class PotencialE {

    // Atributos privados para encapsulamiento
    private Integer id; // Usar camelCase para nombres de variables
    private double campoE;
    private double distanciaD;
    private double resultado;

    // Constructor por defecto
    public PotencialE() {
    }

    // Constructor con parámetros
    public PotencialE(Integer id, double campoE, double distanciaD, double resultado) {
        this.id = id;
        this.campoE = campoE;
        this.distanciaD = distanciaD;
        this.resultado = resultado;
    }

    // Métodos getter y setter para cada atributo
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCampoE() {
        return campoE;
    }

    public void setCampoE(double campoE) {
        this.campoE = campoE;
    }

    public double getDistanciaD() {
        return distanciaD;
    }

    public void setDistanciaD(double distanciaD) {
        this.distanciaD = distanciaD;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    // Método para calcular el potencial eléctrico (ejemplo de mejora)
    public void calcularPotencial() {
        // Suponiendo una fórmula para el cálculo del potencial eléctrico
        this.resultado = this.campoE * this.distanciaD;
    }

}
