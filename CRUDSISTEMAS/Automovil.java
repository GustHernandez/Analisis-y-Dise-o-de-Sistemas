public class Automovil extends Vehiculo{
    // Atributos de la clase Automovil
    private int numPuertas;
    private String marca;
    /*
     * Constructor de la clase Automovil
     */
    public Automovil(int id, int idPersona, String marca, String modelo, String placa, int numPuertas){
        super(id, idPersona, placa, modelo);
        this.marca = marca;
        this.numPuertas = numPuertas;
    }
    /*
     * Métodos Getters y Setters
     */
    public int getNumPuertas() {
        return numPuertas;
    }
    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Automovil [numPuertas=" + numPuertas + ", marca=" + marca + "]";
    }
}
