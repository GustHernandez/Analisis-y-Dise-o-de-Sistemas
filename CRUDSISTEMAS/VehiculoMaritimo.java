public class VehiculoMaritimo extends Vehiculo{
    // Atributos de la clase VehiculoMaritimo
    private double peso;
    private String marca;
    private int capacidadPasajeros;
    /*
     * Constructor de la clase VehiculoMaritimo
     */
    public VehiculoMaritimo(int id, int idPersona, String marca, String modelo, String placa, double peso, int capacidadPasajeros){
        super(id, idPersona, placa, modelo);
        this.peso = peso;
        this.marca = marca;
        this.capacidadPasajeros = capacidadPasajeros;
    }
    /*
     * Métodos Getters y Setters
     */
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "VehiculoMaritimo [peso=" + peso + ", marca=" + marca + ", capacidadPasajeros=" + capacidadPasajeros
                + "]";
    }
}
