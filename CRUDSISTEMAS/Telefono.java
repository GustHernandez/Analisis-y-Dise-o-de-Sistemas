public class Telefono {
    // Atributos de la clase Telefono
    private int id;
    private int idPersona;
    private String numero;
    /*
     * Constructor de la clase Telefono
     */
    public Telefono(int id, int idPersona, String numero) {
        this.id = id;
        this.idPersona = idPersona;
        this.numero = numero;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "Telefono [id=" + id + ", idPersona=" + idPersona + ", numero=" + numero + "]";
    }
}

