public class Bicicleta extends Vehiculo{
    // Atributos de la clase Bicicleta
    private int anio;
    private double tamanioRueda;

    /*
     * Constructor de la clase Bicicleta
     */
    public Bicicleta(int id, int idPersona, String tipo, String modelo, int anio, double tamanioRueda){
        super(id, idPersona, tipo, modelo);
        this.anio = anio;
        this.tamanioRueda = tamanioRueda;
    }

    /*
     * Método Getters y Setters
     */
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getTamanioRueda() {
        return tamanioRueda;
    }

    public void setTamanioRueda(double tamanioRueda) {
        this.tamanioRueda = tamanioRueda;
    }

    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "Bicicleta [anio=" + anio + ", tamanioRueda=" + tamanioRueda + "]";
    }
}
