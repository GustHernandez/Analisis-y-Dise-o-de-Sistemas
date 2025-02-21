import java.util.ArrayList;
import java.util.List;

public class Persona {
    // Atributos de la clase Persona
    private int id;
    private String nombre;
    private String direccion;
    private List<String> telefonos;
    private List<Vehiculo> vehiculos;
    /*
     * Constructor de la clase Persona
     */
    public Persona(int id, String nombre, String direccion, List<String> telefonos, List<Vehiculo> vehiculos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonos = telefonos;
        this.vehiculos = (vehiculos != null) ? vehiculos : new ArrayList<Vehiculo>(); // Asegurarse de que no sea null
    }
    /*
     * Métodos Getters y Setters
     */
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }
    /*
     * Método toString
     */
    @Override
    public String toString() {
        return "Persona [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", telefonos=" + telefonos
                + ", vehiculos=" + vehiculos + "]";
    }
}
