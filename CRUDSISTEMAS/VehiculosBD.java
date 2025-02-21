import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculosBD {
    // Atributos de la clase VehiculosBD
    private static final String URL = "jdbc:mysql://localhost:3306/mi_base_de_datos";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // Método privado para establecer conección con la base de datos
    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    /*
     * Método para insertar un vehiculo
     */
    public static void insertarVehiculo(int idPersona, String tipo, String modelo) throws SQLException {
        String sql = "INSERT INTO Vehiculos (id_persona, tipo, modelo) VALUES (?, ?, ?)";
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            statement.setString(2, tipo);
            statement.setString(3, modelo);
            statement.executeUpdate();
        }
    }
    /*
     * Método para obtener los vehiculos
     */
    public static List<Vehiculo> obtenerVehiculos(int idPersona) throws SQLException {
        String sql = "SELECT * FROM Vehiculos WHERE id_persona = ?";
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String tipo = resultSet.getString("tipo");
                    String modelo = resultSet.getString("modelo");
                    vehiculos.add(new Vehiculo(id, idPersona, tipo, modelo));
                }
            }
        }
        return vehiculos;
    }
    /*
     * Método para eliminar vehiculos
     */
    public static void eliminarVehiculos(int idPersona) throws SQLException {
        String sql = "DELETE FROM Vehiculos WHERE id_persona = ?";
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPersona);
            statement.executeUpdate();
        }
    }
    /*
     * Método para actualizar los vehiculos
     */
    public static void actualizarVehiculos(int idPersona, List<Vehiculo> vehiculos) throws SQLException {
        eliminarVehiculos(idPersona);
        for (Vehiculo vehiculo : vehiculos) {
            insertarVehiculo(idPersona, vehiculo.getTipo(), vehiculo.getModelo());
        }
    }
}
