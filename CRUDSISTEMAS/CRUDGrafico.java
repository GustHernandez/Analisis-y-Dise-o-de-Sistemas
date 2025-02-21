import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDGrafico extends Application {
    // Atributos de la clase CRUDGrafico
    private TableView<Persona> tableView = new TableView<>();
    private TextField nombreField = new TextField();
    private TextField direccionField = new TextField();
    private TextField telefonoField = new TextField();
    private TextField idField = new TextField();
    private List<String> telefonosList = new ArrayList<>();
    private ListView<String> listaPersonas = new ListView<>();
    private Map<String, Integer> personaIdMap = new HashMap<>();

    /*
     * Método que permite inicializar la GUI
     */
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setPadding(new Insets(15));

        Image image = new Image("personas.png"); 
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(25);  
        imageView.setLayoutY(40); 
        imageView.setFitWidth(160);  
        imageView.setFitHeight(160); 

        nombreField.setPromptText("Nombre");
        nombreField.setLayoutX(200);
        nombreField.setLayoutY(20);
        
        direccionField.setPromptText("Dirección");
        direccionField.setLayoutX(200);
        direccionField.setLayoutY(60);

        telefonoField.setPromptText("Teléfonos");
        telefonoField.setLayoutX(200);
        telefonoField.setLayoutY(100);

        TextField tipoVehiculoField = new TextField();
        tipoVehiculoField.setPromptText("Tipo de vehículo");
        tipoVehiculoField.setLayoutX(200);
        tipoVehiculoField.setLayoutY(140);

        TextField modeloVehiculoField = new TextField();
        modeloVehiculoField.setPromptText("Modelo del vehículo");
        modeloVehiculoField.setLayoutX(200);
        modeloVehiculoField.setLayoutY(180);

        Button agregarVehiculoBtn = new Button("Añadir Vehículo");
        agregarVehiculoBtn.setLayoutX(200);
        agregarVehiculoBtn.setLayoutY(220);

        ListView<String> listaVehiculos = new ListView<>();
        listaVehiculos.setPrefHeight(100);
        listaVehiculos.setLayoutX(70);
        listaVehiculos.setLayoutY(260);

        Button agregarBtn = new Button("Agregar Persona");
        agregarBtn.setLayoutX(20);
        agregarBtn.setLayoutY(380);

        Button actualizarBtn = new Button("Actualizar Persona");
        actualizarBtn.setLayoutX(128);
        actualizarBtn.setLayoutY(380);

        Button eliminarBtn = new Button("Eliminar Persona");
        eliminarBtn.setLayoutX(245);
        eliminarBtn.setLayoutY(380);

        listaPersonas.setLayoutX(70);
        listaPersonas.setLayoutY(420);
        listaPersonas.setPrefHeight(160);

        root.getChildren().add(imageView);
        root.getChildren().addAll(nombreField, direccionField, telefonoField, tipoVehiculoField, modeloVehiculoField, agregarVehiculoBtn, listaVehiculos, agregarBtn, actualizarBtn, eliminarBtn, listaPersonas);

        actualizarLista();

        agregarVehiculoBtn.setOnAction(e -> {
            String tipoVehiculo = tipoVehiculoField.getText();
            String modelo = modeloVehiculoField.getText();
            if (!tipoVehiculo.isEmpty() && !modelo.isEmpty() && !listaVehiculos.getItems().contains(tipoVehiculo + " (" + modelo + ")")) {
                listaVehiculos.getItems().add(tipoVehiculo + " (" + modelo + ")");
                tipoVehiculoField.clear();
                modeloVehiculoField.clear();
            }
        });

        agregarBtn.setOnAction(e -> {
            try {
                String nombre = nombreField.getText();
                String direccion = direccionField.getText();
                List<String> telefonos = Arrays.asList(telefonoField.getText().isEmpty() ? new String[0] : telefonoField.getText().split(","));
                
                // Crear lista de vehículos
                List<Vehiculo> vehiculos = new ArrayList<>();
                for (String vehiculoInfo : listaVehiculos.getItems()) {
                    String[] partes = vehiculoInfo.split(" \\(");
                    if (partes.length == 2) {
                        String tipo = partes[0];
                        String modelo = partes[1].replace(")", "");
                        vehiculos.add(new Vehiculo(0, 0, tipo, modelo));
                    }
                }
        
                System.out.println("Agregando persona: " + nombre + ", " + direccion + ", " + telefonos + ", " + vehiculos);
                PersonaBD.insertarPersona(nombre, direccion, telefonos, vehiculos);
                listaVehiculos.getItems().clear(); // Limpiar la lista de vehículos
                actualizarLista(); // Actualizar la lista de personas en la interfaz
            } catch (SQLException ex) {
                System.out.println("Error SQL: " + ex.getMessage());
            }
        });
        
        actualizarBtn.setOnAction(e -> {
            try {
                String selectedPersona = listaPersonas.getSelectionModel().getSelectedItem();
                if (selectedPersona != null) {
                    int idPersona = personaIdMap.get(selectedPersona.split(" - ")[0]);
                    String nombre = nombreField.getText();
                    String direccion = direccionField.getText();
                    List<String> telefonos = Arrays.asList(telefonoField.getText().split(","));
                    
                    // Extraer los vehículos de la lista
                    List<Vehiculo> vehiculos = new ArrayList<>();
                    for (String vehiculoInfo : listaVehiculos.getItems()) {
                        String[] partes = vehiculoInfo.split(" \\(");
                        if (partes.length == 2) {
                            String tipo = partes[0];
                            String modelo = partes[1].replace(")", "");
                            vehiculos.add(new Vehiculo(0, idPersona, tipo, modelo));
                        }
                    }
                    
                    System.out.println("Actualizando persona ID " + idPersona + ": " + nombre + ", " + direccion + ", " + telefonos + ", " + vehiculos);
                    PersonaBD.actualizarPersona(idPersona, nombre, direccion, telefonos, vehiculos);
                    actualizarLista();
                }
            } catch (SQLException ex) {
                System.out.println("Error SQL: " + ex.getMessage());
            }
        });
        

        eliminarBtn.setOnAction(e -> {
            try {
                String selectedPersona = listaPersonas.getSelectionModel().getSelectedItem();
                if (selectedPersona != null) {
                    int idPersona = personaIdMap.get(selectedPersona.split(" - ")[0]);
                    System.out.println("Eliminando persona ID: " + idPersona);
                    PersonaBD.eliminarPersona(idPersona);
                    actualizarLista();
                }
            } catch (SQLException ex) {
                System.out.println("Error SQL: " + ex.getMessage());
            }
        });
        agregarVehiculoBtn.setStyle("-fx-background-color:rgb(98, 55, 167); -fx-text-fill: white;");
        agregarBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        actualizarBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        eliminarBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("CRUD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /*
     * Métodop privado para actualizar la lista
     */
    private void actualizarLista() {
        try {
            List<Persona> personas = PersonaBD.obtenerPersonas();
            listaPersonas.getItems().clear();
            personaIdMap.clear();

            for (Persona persona : personas) {
                String info = persona.getNombre() + " - " + persona.getDireccion() + " - " + String.join(", ", persona.getTelefonos());

                List<String> vehiculosInfo = new ArrayList<>();
                for (Vehiculo vehiculo : persona.getVehiculos()) {
                    vehiculosInfo.add(vehiculo.getTipo() + " (" + vehiculo.getModelo() + ")");
                }
                if (!vehiculosInfo.isEmpty()) {
                    info += " - " + String.join(", ", vehiculosInfo);
                }

                listaPersonas.getItems().add(info);
                personaIdMap.put(persona.getNombre(), persona.getId());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Método main
     */
    public static void main(String[] args) {
        launch(args);
    }
}
