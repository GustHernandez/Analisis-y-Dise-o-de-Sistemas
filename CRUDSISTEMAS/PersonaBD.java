import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PersonaBD {
    // Atributos de la clase PersonaBD
    private static final String URL = "jdbc:mysql://localhost:3306/mi_base_de_datos";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    /*
     *Método privado para establecer conección con la base de datos
     */
    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /*
     * Método para insertar una persona en la base de datos
     */
    public static void insertarPersona(String nombre, String direccion, List<String> telefonos, List<Vehiculo> vehiculos) throws SQLException {
        String sqlInsertarPersona = "INSERT INTO contactos (nombre, direccion) VALUES (?, ?)";
        
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sqlInsertarPersona, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, nombre);  
            statement.setString(2, direccion); 
            statement.executeUpdate();  
            
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int idPersona = resultSet.getInt(1);
                    
                    insertarTelefonos(idPersona, telefonos);
                    insertarVehiculos(idPersona, vehiculos);
                }
            }
        }
    }
    /*
     * Método para insertar los teléfonos de la persona
     */
    private static void insertarTelefonos(int idPersona, List<String> telefonos) throws SQLException {
        for (String telefono : telefonos) {
            TelefonoBD.insertarTelefono(idPersona, telefono);
        }
    }

    /*
     * Método para insertar los vehículos de la persona
     */
    private static void insertarVehiculos(int idPersona, List<Vehiculo> vehiculos) throws SQLException {
        for (Vehiculo vehiculo : vehiculos) {
            VehiculosBD.insertarVehiculo(idPersona, vehiculo.getTipo(), vehiculo.getModelo());
        }
    }

    /*
     * Método para obtener una lista de todas las personas en la base de datos
     */
    public static List<Persona> obtenerPersonas() throws SQLException {
        String sql = "SELECT * FROM contactos";
        List<Persona> personas = new ArrayList<>();
        
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                
                List<String> telefonos = TelefonoBD.obtenerTelefonos(id);
                List<Vehiculo> vehiculos = VehiculosBD.obtenerVehiculos(id);
                
                Persona persona = new Persona(id, nombre, direccion, telefonos, vehiculos);
                personas.add(persona);
            }
        }
        return personas;
    }

    /*
     *  Método para obtener una persona por su ID
     */
    public static Persona obtenerPersonaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM contactos WHERE id = ?";
        
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String direccion = resultSet.getString("direccion");

                    List<String> telefonos = TelefonoBD.obtenerTelefonos(id);
                    List<Vehiculo> vehiculos = VehiculosBD.obtenerVehiculos(id);

                    return new Persona(id, nombre, direccion, telefonos, vehiculos);
                }
            }
        }
        return null;
    }

    /*
     * Método para eliminar una persona de la base de datos
     */
    public static void eliminarPersona(int id) throws SQLException {
        try (Connection connection = obtenerConexion()) {
            connection.setAutoCommit(false);  
            try {
                TelefonoBD.eliminarTelefono(id);
                VehiculosBD.eliminarVehiculos(id);

                String sqlEliminarPersona = "DELETE FROM contactos WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sqlEliminarPersona)) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    connection.commit();  
                } catch (SQLException e) {
                    connection.rollback();  
                    throw e;
                }
            } catch (SQLException e) {
                connection.rollback();  
                throw e;
            }
        }
    }

    /*
     * Método para actualizar la información de una persona
     */
    public static void actualizarPersona(int id, String nombre, String direccion, List<String> telefonos, List<Vehiculo> vehiculos) throws SQLException {
        String sqlActualizarPersona = "UPDATE contactos SET nombre = ?, direccion = ? WHERE id = ?";
        
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sqlActualizarPersona)) {
            
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setInt(3, id);
            statement.executeUpdate();  

            TelefonoBD.eliminarTelefono(id);
            for (String telefono : telefonos) {
                TelefonoBD.insertarTelefono(id, telefono);
            }

            VehiculosBD.actualizarVehiculos(id, vehiculos);
        }
    }
}
