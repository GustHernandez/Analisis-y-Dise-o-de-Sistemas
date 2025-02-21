import java.sql.*;
public class basedatos {
    public static void main(String[] args) {
        // URL de la base de datos, usuario y contraseña
        String url = "jdbc:mysql://localhost:3306/mi_base_de_datos";
        String usuario = "root"; // Cambia según tu configuración
        String contrasena = "12345678";  // Cambia según tu configuración

        // Conexión y declaraciones
        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena)) {
            // Crear una sentencia para insertar datos
            String insertarSQL = "INSERT INTO contactos (nombre, direccion, telefono) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertarSQL)) {
                stmt.setString(1, "Juanito Sarmiento");
                stmt.setString(2, "Calle Falsa 123");
                stmt.setString(3, "555-1234");
                stmt.executeUpdate();
                System.out.println("Contacto agregado exitosamente.");
            }

            // Crear una sentencia para consultar los registros
            String consultaSQL = "SELECT * FROM contactos";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(consultaSQL)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    System.out.println("ID: " + id + ", Nombre: " + nombre + ", Dirección: " + direccion + ", Teléfono: " + telefono);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void agregarPersona() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarPersona'");
    }
}
