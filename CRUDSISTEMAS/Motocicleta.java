public class Motocicleta extends Vehiculo{
    // Atributos de la clase Motocicleta
    private String placa;
    private double velocidadMax;
    /* 
     * Constructor de la clase Motocicleta
     */
    public Motocicleta(int id, int idPersona, String tipo, String modelo, String placa, double velocidadMax){
        super(id, idPersona, tipo, modelo);
        this.placa = placa;
        this.velocidadMax = velocidadMax;
    }

    /* 
     * Métodos Getters y Setters
     */
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getVelocidadMax() {
        return velocidadMax;
    }

    public void setVelocidadMax(double velocidadMax) {
        this.velocidadMax = velocidadMax;
    }

    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "Motocicleta [placa=" + placa + ", velocidadMax=" + velocidadMax + "]";
    }

}
