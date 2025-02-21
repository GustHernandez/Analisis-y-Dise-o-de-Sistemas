public class CamionDeCarga extends Vehiculo{
    // Atributos de la clase CamionDeCarga
    private String capacidadCarga;
    private String marca;
    /*
     * Constructor de la clase CamionDeCarga
     */
    public CamionDeCarga(int id, int idPersona, String marca, String modelo, String placa, String capacidadCarga){
        super(id, idPersona, placa, modelo);
        this.marca = marca;
        this.capacidadCarga = capacidadCarga;
    }

    /*
     * Métodos Getters y Setters
     */
    public String getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(String capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "CamionDeCarga [capacidadCarga=" + capacidadCarga + ", marca=" + marca + "]";
    }

}
