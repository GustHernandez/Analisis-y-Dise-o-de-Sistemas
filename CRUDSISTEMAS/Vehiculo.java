public class Vehiculo {
    // Atributos de la clase Vehiculo
    private int id;
    private int idPersona;
    private String tipo; 
    private String modelo;
    /*
     * Constructor de la clase Vehiculo
     */
    public Vehiculo(int id, int idPersona, String tipo, String modelo) {
        this.id = id;
        this.idPersona = idPersona;
        this.tipo = tipo;
        this.modelo = modelo;
    }
    /*
     * Métodos Getters y Setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "Vehiculo [id=" + id + ", idPersona=" + idPersona + ", tipo=" + tipo + ", modelo=" + modelo + "]";
    }

}
