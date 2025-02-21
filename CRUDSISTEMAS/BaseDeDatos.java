import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class BaseDeDatos {
    private static Connection connection;

    public BaseDeDatos() {}

    // Método para agregar una persona y sus teléfonos
    public static void agregarPersona(String nombre, String direccion, List<String> telefonos) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "root", "12345678");

            // Insertar contacto principal
            String insertarSQL = "INSERT INTO contactos (Nombre, Direccion) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertarSQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, nombre);
                statement.setString(2, direccion);
                statement.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);

                    // Insertar los teléfonos asociados
                    String insertarTelefonoSQL = "INSERT INTO telefonos (contactos, telefonos) VALUES (?, ?)";
                    try (PreparedStatement telefonoStatement = connection.prepareStatement(insertarTelefonoSQL)) {
                        for (String telefono : telefonos) {
                            telefonoStatement.setInt(1, id);
                            telefonoStatement.setString(2, telefono);
                            telefonoStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una persona y sus teléfonos
    public static void eliminarPersona(int id) {
        int flag;
        String eliminarTelefonosSQL = "DELETE FROM telefonos WHERE contacto_id = ?";
        String eliminarContactoSQL = "DELETE FROM contactos WHERE Id = ?";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "root", "12345678");

            // Primero, eliminar los teléfonos asociados
            try (PreparedStatement eliminarTelefonoStatement = connection.prepareStatement(eliminarTelefonosSQL)) {
                eliminarTelefonoStatement.setInt(1, id);
                eliminarTelefonoStatement.executeUpdate();
            }

            // Luego, eliminar el contacto
            try (PreparedStatement statement = connection.prepareStatement(eliminarContactoSQL)) {
                statement.setInt(1, id);
                flag = statement.executeUpdate();
                if (flag > 0) {
                    System.out.println("Se eliminó el usuario");
                } else {
                    System.out.println("No se pudo eliminar");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para editar una persona y sus teléfonos
    public static void editarPersona(int id, String nombre, String direccion, List<String> telefonos) {
        String consultaSQL = "SELECT Id FROM contactos WHERE Id = ?";
        String actualizarSQL = "UPDATE contactos SET Nombre = ?, Direccion = ? WHERE Id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "root", "12345678")) {
            // Verificar si el usuario existe antes de actualizar
            try (PreparedStatement statement = connection.prepareStatement(consultaSQL)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        System.out.println("Usuario no encontrado");
                        return;
                    }
                }
            }

            // Actualizar los datos del usuario
            try (PreparedStatement statement = connection.prepareStatement(actualizarSQL)) {
                statement.setString(1, nombre);
                statement.setString(2, direccion);
                statement.setInt(3, id);

                int flag = statement.executeUpdate();
                if (flag > 0) {
                    // Eliminar teléfonos existentes y agregar los nuevos
                    String eliminarTelefonosSQL = "DELETE FROM telefonos WHERE contacto_id = ?";
                    try (PreparedStatement eliminarStatement = connection.prepareStatement(eliminarTelefonosSQL)) {
                        eliminarStatement.setInt(1, id);
                        eliminarStatement.executeUpdate();
                    }

                    // Insertar los nuevos teléfonos
                    String insertarTelefonoSQL = "INSERT INTO telefonos (contacto_id, Telefono) VALUES (?, ?)";
                    try (PreparedStatement telefonoStatement = connection.prepareStatement(insertarTelefonoSQL)) {
                        for (String telefono : telefonos) {
                            telefonoStatement.setInt(1, id);
                            telefonoStatement.setString(2, telefono);
                            telefonoStatement.executeUpdate();
                        }
                    }

                    System.out.println("Se actualizó correctamente");
                } else {
                    System.out.println("No se pudo actualizar");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar el historial de contactos
    public static void historial() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "root", "12345678");
            String consultaSQL = "SELECT c.Id, c.Nombre, c.Direccion, GROUP_CONCAT(t.Telefono) AS Telefonos "
                               + "FROM contactos c "
                               + "LEFT JOIN telefonos t ON c.Id = t.ContactoId "
                               + "GROUP BY c.Id;";

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(consultaSQL);
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String nombre = resultSet.getString("Nombre");
                    String direccion = resultSet.getString("Direccion");
                    String telefonos = resultSet.getString("Telefonos");
                    System.out.println("Id: " + id + ", Nombre: " + nombre + ", Dirección: " + direccion + ", Teléfonos: " + telefonos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name;
        String ad;
        String tel;
        BaseDeDatos base = new BaseDeDatos();

        // Ejemplo de uso
        System.out.println("\nHistorial de contactos:");
        historial();
        // Puedes agregar, eliminar o editar personas aquí llamando a los métodos adecuados
        // base.agregarPersona("Nuevo Usuario", "Dirección", List.of("123-456", "789-012"));
        // base.eliminarPersona(7);
        // base.editarPersona(1, "Nuevo Nombre", "Nueva Dirección", List.of("345-678", "987-654"));
        System.out.println();
    }
}
