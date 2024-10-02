package co.edu.uniquindio.notas.controladores;
import co.edu.uniquindio.notas.modelo.Contacto;
import co.edu.uniquindio.notas.modelo.ContactoPrincipal;
import com.sun.javafx.charts.Legend;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import java.util.ResourceBundle;


public class InicioControlador implements Initializable {
    public Button btnNuevoContacto;
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNumero;

    @FXML
    private DatePicker dateNacimiento;

    @FXML
    private TextField txtUrlFoto;

    @FXML
    private ImageView imgFotoPerfil;

    @FXML
    private TableColumn<Contacto, String> colUrlFoto;  // Columna para la URL


    @FXML
    private Label lblNombre;
    @FXML
    private Label lblNumero;












    private final ContactoPrincipal contactoPrincipal;

    @FXML
    private TableView<Contacto> tablaNotas;


    @FXML
    private TableColumn<Contacto, String> colNombre;

    @FXML
    private TableColumn<Contacto, String> colApellido;

    @FXML
    private TableColumn<Contacto, String> colCorreo;

    @FXML
    private TableColumn<Contacto, String> colNumero;

    @FXML
    private TableColumn<Contacto, String> colNacimiento;

    @FXML
    private TableColumn<Contacto, Image> colFotoPerfil;









    @FXML
    private TableColumn<Contacto, String> colFecha;
    @FXML
    private TableColumn<Contacto, Integer> colId;
    private Legend.LegendItem txtId;


    public InicioControlador() {
        contactoPrincipal = new ContactoPrincipal();
    }

    @FXML
    public void crearContacto(ActionEvent e) {
        try {
            // Recoger los datos de los campos de texto y el DatePicker
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();
            String numero = txtNumero.getText();
            LocalDate fechaNacimiento = dateNacimiento.getValue();
            String urlFoto = txtUrlFoto.getText();

            // Comprobación de campos vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || numero.isEmpty() || fechaNacimiento == null) {
                throw new Exception("Todos los campos deben estar llenos.");
            }

            // Comprobación de que el número de teléfono sea válido (solo dígitos)
            if (!numero.matches("\\d+")) {
                throw new Exception("El número de teléfono debe contener solo dígitos.");
            }

            // Verificar que no exista otro contacto con el mismo número
            if (contactoPrincipal.existeContactoPorNumero(numero)) {
                throw new Exception("Ya existe un contacto con este número.");
            }

            // Verificar que no exista otro contacto con el mismo correo
            if (contactoPrincipal.existeContactoPorCorreo(correo)) {
                throw new Exception("Ya existe un contacto con este correo.");
            }

            // Obtener la fecha de creación (actual)
            LocalDate fechaCreacion = LocalDate.now();

            // Usar el método en ContactoPrincipal para agregar un contacto
            contactoPrincipal.agregarContacto(nombre, apellido, correo, numero, fechaNacimiento, fechaCreacion, urlFoto);
            Image imagenPerfil = new Image(urlFoto);
            imgFotoPerfil.setImage(imagenPerfil);

            // Mostrar alerta de éxito
            mostrarAlerta("Contacto creado correctamente", Alert.AlertType.INFORMATION);

            // Limpiar los campos de texto y el DatePicker
            txtNombre.clear();
            txtApellido.clear();
            txtCorreo.clear();
            txtNumero.clear();
            dateNacimiento.setValue(null);
            txtUrlFoto.clear();

            // Actualizar la tabla
            actualizarTabla();
            actualizarContactoReciente();

        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){


        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        colNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaNacimiento().toString()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCreacion().toString()));
        colFotoPerfil.setCellValueFactory(new PropertyValueFactory<>("fotoPerfil"));

        colFotoPerfil.setCellFactory(column -> new TableCell<Contacto, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });

        actualizarTabla();
        // Agregar datos a la tabla



    }
    private void actualizarTabla() {
        tablaNotas.setItems(FXCollections.observableArrayList(contactoPrincipal.listarNotas()));
        tablaNotas.refresh();
    }

    private void actualizarContactoReciente() {
        if (!contactoPrincipal.listarNotas().isEmpty()) {
            Contacto ultimoContacto = contactoPrincipal.listarNotas().get(contactoPrincipal.listarNotas().size() - 1);

            lblNombre.setText(ultimoContacto.getNombre());

            lblNumero.setText(ultimoContacto.getNumero());

            if (ultimoContacto.getUrlFoto() != null && !ultimoContacto.getUrlFoto().isEmpty()) {
                Image imagenPerfil = new Image(ultimoContacto.getUrlFoto());
                imgFotoPerfil.setImage(imagenPerfil);
            } else {
                imgFotoPerfil.setImage(null);  // Limpiar la imagen si no hay URL
            }
        }
    }
    // Método para eliminar contacto basado en número
    @FXML
    public void eliminarContacto(ActionEvent e) {
        try {
            // Cuadro de diálogo para ingresar el número del contacto a eliminar
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Eliminar Contacto");
            dialog.setHeaderText("Ingrese el número del contacto que desea eliminar:");
            dialog.setContentText("Número:");

            // Obtener el número ingresado
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String numero = result.get();

                // Verificar si el contacto existe
                if (!contactoPrincipal.existeContacto(numero)) {
                    mostrarAlerta("El contacto no existe.", Alert.AlertType.WARNING);
                    return;
                }

                // Obtener el contacto antes de eliminar
                Contacto contactoEliminado = contactoPrincipal.obtenerContacto(numero);

                // Eliminar contacto
                contactoPrincipal.eliminarContacto(numero);
                limpiarCampos();
                actualizarTabla();

                // Verificar si el contacto eliminado es el más reciente
                if (contactoEliminado != null && contactoEliminado.getNumero().equals(lblNumero.getText())) {
                    lblNombre.setText("");
                    lblNumero.setText("");
                    imgFotoPerfil.setImage(null); // Limpiar imagen
                }

                mostrarAlerta("Contacto eliminado correctamente", Alert.AlertType.INFORMATION);
            }

        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }


    // Método para actualizar contacto basado en número
    @FXML
    public void actualizarContacto(ActionEvent e) {
        try {
            // Cuadro de diálogo para ingresar el número del contacto a actualizar
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Contacto");
            dialog.setHeaderText("Ingrese el número del contacto que desea actualizar:");
            dialog.setContentText("Número:");

            // Obtener el número ingresado
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String numero = result.get();

                // Verificar si el contacto existe
                if (!contactoPrincipal.existeContacto(numero)) {
                    mostrarAlerta("El contacto no existe.", Alert.AlertType.WARNING);
                    return;
                }

                // Obtener el contacto existente
                Contacto contactoExistente = contactoPrincipal.obtenerContacto(numero);

                // Rellenar los campos con los valores actuales (si el usuario lo desea)
                // Preguntar si quiere ver los datos actuales antes de modificar
                Alert alertDatos = new Alert(Alert.AlertType.INFORMATION);
                alertDatos.setTitle("Datos del Contacto");
                alertDatos.setHeaderText("Datos actuales del contacto:");
                alertDatos.setContentText("Nombre: " + contactoExistente.getNombre() +
                        "\nApellido: " + contactoExistente.getApellido() +
                        "\nCorreo: " + contactoExistente.getCorreo() +
                        "\nFecha de Nacimiento: " + contactoExistente.getFechaNacimiento() +
                        "\nURL Foto: " + contactoExistente.getUrlFoto());
                alertDatos.showAndWait();

                // Rellenar los campos con los valores actuales
                txtNombre.setText(contactoExistente.getNombre());
                txtApellido.setText(contactoExistente.getApellido());
                txtCorreo.setText(contactoExistente.getCorreo());
                dateNacimiento.setValue(contactoExistente.getFechaNacimiento());
                txtUrlFoto.setText(contactoExistente.getUrlFoto());

                // Abrir cuadro de diálogo para confirmar los cambios
                ButtonType confirmar = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alertConfirmar = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea actualizar el contacto?", confirmar, cancelar);
                alertConfirmar.setTitle("Confirmar Actualización");
                Optional<ButtonType> respuesta = alertConfirmar.showAndWait();

                if (respuesta.isPresent() && respuesta.get() == confirmar) {
                    // Obtener los nuevos valores de los campos
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String correo = txtCorreo.getText();
                    LocalDate fechaNacimiento = dateNacimiento.getValue();
                    String urlFoto = txtUrlFoto.getText();

                    // Verificar que los nuevos valores sean diferentes de los antiguos antes de actualizar
                    if (!nombre.equals(contactoExistente.getNombre()) ||
                            !apellido.equals(contactoExistente.getApellido()) ||
                            !correo.equals(contactoExistente.getCorreo()) ||
                            !fechaNacimiento.equals(contactoExistente.getFechaNacimiento()) ||
                            !urlFoto.equals(contactoExistente.getUrlFoto())) {

                        // Actualizar contacto
                        contactoPrincipal.actualizarContacto(numero, nombre, apellido, correo, fechaNacimiento, urlFoto);
                        limpiarCampos();
                        actualizarTabla(); // Actualizar la tabla después de la modificación

                        // Actualizar la vista del contacto reciente si es necesario
                        if (numero.equals(lblNumero.getText())) {
                            lblNombre.setText(nombre);
                            lblNumero.setText(numero);
                            imgFotoPerfil.setImage(new Image(urlFoto)); // Actualizar imagen de perfil
                        }

                        mostrarAlerta("Contacto actualizado correctamente", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarAlerta("No se realizaron cambios. Los datos son los mismos.", Alert.AlertType.INFORMATION);
                    }
                }
            }
        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }





    // Método para limpiar los campos después de agregar, eliminar o actualizar
    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtNumero.clear();
        dateNacimiento.setValue(null);
        txtUrlFoto.clear();
    }









}
