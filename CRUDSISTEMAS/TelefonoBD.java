import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefonoBD {
    // Atributos de la clase TelefonoBD
    private static final String URL = "jdbc:mysql://localhost:3306/mi_base_de_datos";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    /*
     * Método privado para establecer conección con la base de datos
     */
    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /*
     * Método para insertar un teléfono 
     */
    public static void insertarTelefono(int idPersona, String telefono) throws SQLException {
        String sql = "INSERT INTO Telefonos (id_persona, telefono) VALUES (?, ?)"; 
        try (Connection connection = obtenerConexion();  
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            statement.setString(2, telefono); 
            statement.executeUpdate(); 
        }
    }

    /*
     * Método para obtener la lista de teléfonos 
     */
    public static List<String> obtenerTelefonos(int idPersona) throws SQLException {
        String sql = "SELECT telefono FROM Telefonos WHERE id_persona = ?"; 
        List<String> telefonos = new ArrayList<>(); 
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) { 
            statement.setInt(1, idPersona); 
            try (ResultSet resultSet = statement.executeQuery()) { 
                while (resultSet.next()) {  
                    telefonos.add(resultSet.getString("telefono")); 
                }
            }
        }
        return telefonos; 
    }

    /*
     * Método para eliminar los teléfonos 
     */
    public static void eliminarTelefono(int idPersona) throws SQLException {
        String sql = "DELETE FROM Telefonos WHERE id_persona = ?";
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) { 
            statement.setInt(1, idPersona);
            statement.executeUpdate();
        }
    }
}
