package sample.controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.LocalDateStringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import sample.modelo.Conexion;
import sample.modelo.beanPaciente;
import sample.modelo.beanSucursal;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class controllerPaciente implements Initializable {
    double x=0, y=0;
    private ObservableList<beanPaciente> data = FXCollections.observableArrayList();
    @FXML
    //Botones de la barra de menú principal
    private FontIcon exit, menu, minimice, regresar;

    @FXML
    //Icono de los botones del menú lateral
    private FontIcon icoSucursal, icoPaciente, icoOdontologo, icoCita, icoHistoriaClinica, icoCatalogo;

    @FXML
    //Panel de menú lateral
    private AnchorPane pane1, pane2;

    @FXML
    //Botones del menú lateral
    private JFXButton btnSucursal, btnPaciente, btnOdontologo, btnCita, btnHsitoriaClinica, btnCatalogo, btnRegresar;

    //Elementos propios de la ventana
    @FXML
    private FontIcon regresarOpciones;
    @FXML
    private AnchorPane paneOpciones, paneRegistroPaciente, paneConsultarPaciente, paneActualizarPaciente, paneEliminarPaciente;
    @FXML
    private JFXButton btnRegistrarPaciente,btnConsultarPaciente, btnActualizarPaciente, btnEliminarPaciente;


    ////////////////////////////////////////////////////////////////////////
    ////            LOGICA DEL PANEL REGISTRAR                          ////
    ////////////////////////////////////////////////////////////////////////
    @FXML
    private JFXComboBox cbSede, cbProvincia, cbSexoPaciente, cbEstadoPaciente, cbCanton;
    @FXML
    private JFXTextField txtCedula, txtNombresPaciente, txtApellidosPaciente;
    @FXML
    private DatePicker txtFechaPaciente;
    @FXML
    private JFXTextField txtDireccionPaciente, txtCelularPaciente, txtTelefonoPaciente, txtCorreoPaciente;
    @FXML
    private JFXTextField txtNombresContacto, txtApellidosContacto, txtTelefonoContacto, txtCeularContacto;
    @FXML
    private JFXTextField txtReferido;
    @FXML
    private JFXButton btnRegistrar, btnCancelarRegistrar;

    ////////////////////////////////////////////////////////////////////////
    ////            LOGICA DEL PANEL CONSULTAR                          ////
    ////////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdBuscar;

    @FXML
    private JFXButton btnBuscarPaciente, btnBuscarCancelar;

    @FXML
    private TableView<beanPaciente> tablaDatos;

    ////////////////////////////////////////////////////////////////////////
    ////            LOGICA DEL PANEL ACTUALIZAR                         ////
    ////////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtCiPacienteActualizar;
    @FXML
    private JFXButton btnBuscarActualizar, btnCancelarActualizar, btnActualizarActualizar;
    @FXML
    private TableView<beanPaciente> tablaEditar;

    int index;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Eliminar Sucursal                               ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<beanPaciente> tablaEliminar;

    @FXML
    private JFXButton btnBuscarEliminar, btnCancelarEliminar, btnEliminarEliminar;

    @FXML
    private JFXTextField txtCiEliminar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Para cerrar la ventana
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        //Para minimazar la ventana
        minimice.setOnMouseClicked(event -> {

            Stage s;
            s = (Stage) ((FontIcon) event.getSource()).getScene().getWindow();
            s.setIconified(true);
        });

        //Para regrezar al Login Principal
        regresar.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/menuPrincipal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                Stage s;
                s = (Stage) ((FontIcon) event.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar de Login");
            }
        });

        btnRegresar.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/menuPrincipal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                s = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana");
            }
        });

        btnRegresar.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/menuPrincipal.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                Stage s;
                s = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar de Login");
            }
        });

        //Lógica para el desplazamiento de los menús

        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.35), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.35), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {

            pane1.setVisible(true);
            menu.setVisible(false);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.35), pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(1.0);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.35), pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.35), pane1);
            fadeTransition1.setFromValue(1.0);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.35), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
            menu.setVisible(true);
            pane1.setVisible(false);
        });
        ////////////////////////////////////////////////////////////////////////
        ////        LOGICA PARA REGRESAR A CUALQUIERA DE LAS VENTANAS       ////
        ////////////////////////////////////////////////////////////////////////
        btnSucursal.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/sucursal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                root.setOnMousePressed(event ->{
                    x=event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });
                s = (Stage) ((JFXButton) mouseEvent.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana de Sucursal");
            }
        });

        icoSucursal.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/sucursal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                root.setOnMousePressed(event ->{
                    x=event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });
                s = (Stage) ((FontIcon) mouseEvent.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana de Sucursal");
            }
        });

        btnPaciente.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/paciente.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                root.setOnMousePressed(event ->{
                    x=event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });
                s = (Stage) ((JFXButton) mouseEvent.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana de Paciente");
            }
        });

        icoPaciente.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/paciente.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                root.setOnMousePressed(event ->{
                    x=event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });
                s = (Stage) ((FontIcon) mouseEvent.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana de Paciente");
            }
        });

        btnOdontologo.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/odontologo.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                root.setOnMousePressed(event ->{
                    x=event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });
                s = (Stage) ((JFXButton) mouseEvent.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana de Odontólogo");
            }
        });

        icoOdontologo.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/odontologo.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                Stage s;
                root.setOnMousePressed(event ->{
                    x=event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });
                s = (Stage) ((FontIcon) mouseEvent.getSource()).getScene().getWindow();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al cargar la ventana de Odontólogo");
            }
        });


        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA OCULTAR PANELES                         ////
        ////////////////////////////////////////////////////////////////////////
        regresarOpciones.setOnMouseClicked(mouseEvent -> {
            paneActualizarPaciente.setVisible(false);
            paneEliminarPaciente.setVisible(false);
            paneConsultarPaciente.setVisible(false);
            paneRegistroPaciente.setVisible(false);
            paneOpciones.setVisible(true);
            regresarOpciones.setVisible(false);
            regresar.setVisible(true);
        });

        btnConsultarPaciente.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneConsultarPaciente.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaDatos.setVisible(false);
            tablaDatos.getItems().clear();
            tablaDatos.getColumns().clear();
            this.data.clear();
        });

        btnActualizarPaciente.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneActualizarPaciente.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaEditar.setVisible(false);
            tablaEditar.getItems().clear();
            tablaEditar.getColumns().clear();
            this.data.clear();

        });

        btnEliminarPaciente.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarPaciente.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaEliminar.setVisible(false);
            tablaEliminar.getItems().clear();
            tablaEliminar.getColumns().clear();
            this.data.clear();
        });

        cbProvincia.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    String nombreProv = (String) cbProvincia.getSelectionModel().getSelectedItem();
                    try{
                        PreparedStatement ps;
                        ResultSet rs;

                        Connection con = Conexion.getConexion();
                        ps = con.prepareStatement("SELECT id_prov FROM Provincia WHERE nombre=?");
                        ps.setString(1, nombreProv);
                        rs = ps.executeQuery();
                        int idProv=-1;
                        while (rs.next()){
                            idProv = Integer.parseInt(rs.getString("id_prov"));
                        }
                        ps = con.prepareStatement("SELECT nombre FROM canton where id_prov=?");
                        ps.setInt(1, idProv);
                        rs = ps.executeQuery();
                        while(rs.next()){
                            cbCanton.getItems().add(rs.getString(1));
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }else{
                    cbCanton.getSelectionModel().clearSelection();
                    cbCanton.getItems().clear();
                    cbCanton.setPromptText("Cantón");
                }
            }
        });


        btnRegistrarPaciente.setOnMouseClicked(mouseEvent -> {
            //OCULTAMOS LOS PANELES
            paneOpciones.setVisible(false);
            paneRegistroPaciente.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            //CARGAMOS LOS COMBOS
            cargarSucursales();
            cargarProvincias();
            cargarSexo();
            cargarEstado();
            cbCanton.getItems().clear();

        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtFechaPaciente.setConverter(new LocalDateStringConverter(formatter,null));

        txtFechaPaciente.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    System.out.println(txtFechaPaciente.getValue().format(formatter));
                    System.out.println(txtFechaPaciente.getValue().format(formatter));
                }
            }
        });

        btnRegistrar.setOnMouseClicked(mouseEvent -> {
            //CALCULAMOS LA EDAD
            int añoActual = LocalDate.now().getYear();
            int añoNac = txtFechaPaciente.getValue().getYear();
            int edad = añoActual-añoNac;
            beanPaciente newPac = new beanPaciente(
                    txtCedula.getText(),
                    txtNombresPaciente.getText(),
                    txtApellidosPaciente.getText(),
                    edad,
                    txtFechaPaciente.getValue().format(formatter),
                    txtDireccionPaciente.getText(),
                    cbCanton.getValue().toString(),
                    txtTelefonoPaciente.getText(),
                    txtCelularPaciente.getText(),
                    txtCorreoPaciente.getText(),
                    cbSexoPaciente.getValue().toString(),
                    cbEstadoPaciente.getValue().toString(),
                    txtNombresContacto.getText(),
                    txtApellidosContacto.getText(),
                    txtTelefonoContacto.getText(),
                    txtCeularContacto.getText(),
                    txtReferido.getText(),
                    cbSede.getValue().toString());
            try {
                //PACIENTE DATOS
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "begin distributed tran INSERT INTO Paciente_Datos VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);" +
                        " commit");
                ps.setString(1, newPac.getCedula_pac());
                ps.setInt(2, newPac.getEdad_pac());
                ps.setString(3, newPac.getFechaNacimiento_pac());
                ps.setString(4, newPac.getDireccion_pac());
                ps.setString(5, newPac.getCanton_pac());
                ps.setString(6, newPac.getTelefonoFijo_pac());
                ps.setString(7, newPac.getCelular_pac());
                ps.setString(8, newPac.getCorreoElectronico_pac());
                ps.setString(9, newPac.getSexo_pac());
                ps.setString(10, newPac.getEstado_pac());
                ps.setString(11, newPac.getContactoNombres());
                ps.setString(12, newPac.getContactoApellidos());
                ps.setString(13, newPac.getContacto_telefonoFijo());
                ps.setString(14, newPac.getContactoCelular());
                ps.setString(15, newPac.getReferidoPor());
                ps.setString(16, newPac.getId_suc());
                ps.executeUpdate();

                ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "begin distributed tran INSERT INTO Paciente_Cita VALUES (?,?,?,?);" +
                        " commit");
                ps.setString(1, newPac.getCedula_pac());
                ps.setString(2, newPac.getNombre_pac());
                ps.setString(3, newPac.getApellidos_pac());
                ps.setString(4, newPac.getId_suc());
                ps.executeUpdate();

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se registraron correctamente");
                a.setContentText("Se registró al paciente: " + newPac.getNombre_pac().toUpperCase());
                a.showAndWait();
                limpiarGuiRegistrar();
                //Ocultamos el panel
                paneRegistroPaciente.setVisible(false);
                paneOpciones.setVisible(true);
                regresarOpciones.setVisible(false);
                regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        btnCancelarRegistrar.setOnMouseClicked(mouseEvent -> {
            limpiarGuiRegistrar();
            txtCelularPaciente.requestFocus();
        });

        btnBuscarPaciente.setOnMouseClicked(mouseEvent -> {
            llenarColumnas();
            consultarInfoPaciente(txtIdBuscar.getText());
            tablaDatos.setVisible(true);
            tablaDatos.setItems(data);
        });

        btnBuscarCancelar.setOnMouseClicked(mouseEvent -> {
            tablaDatos.setVisible(false);
            tablaDatos.getItems().clear();
            tablaDatos.getColumns().clear();
            this.data.clear();
            txtIdBuscar.requestFocus();
        });

        btnBuscarActualizar.setOnMouseClicked(mouseEvent -> {
            llenarColumnasEditar();
            consultarInfoPaciente(txtCiPacienteActualizar.getText());
            tablaEditar.setVisible(true);
            tablaEditar.setItems(data);
            tablaEditar.setEditable(true);
        });

        btnActualizarActualizar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "UPDATE Paciente_Datos SET " +
                        "edad_pac=?, " +
                        "fechaNacimiento_pac=?, " +
                        "direccion_pac=?," +
                        "canton_pac=?, " +
                        "telefonoFijo_pac=?," +
                        "celular_pac=?, " +
                        "correoElectronico_pac=?," +
                        "sexo_pac=?, " +
                        "estado_pac=?," +
                        "contactoNombres=?, " +
                        "contactoApellidos=?," +
                        "contacto_telefonoFijo=?, " +
                        "contactoCelular=?," +
                        "referidoPor=?, " +
                        "id_suc=?" +
                        " WHERE cedula_pac =?;" +
                        " commit");
                ps.setInt(1,data.get(index).getEdad_pac());
                ps.setString(2,data.get(index).getFechaNacimiento_pac());
                ps.setString(3,data.get(index).getDireccion_pac());
                ps.setString(4,data.get(index).getCanton_pac());
                ps.setString(5,data.get(index).getTelefonoFijo_pac());
                ps.setString(6,data.get(index).getCelular_pac());
                ps.setString(7,data.get(index).getCorreoElectronico_pac());
                ps.setString(8,data.get(index).getSexo_pac());
                ps.setString(9,data.get(index).getEstado_pac());
                ps.setString(10,data.get(index).getContactoNombres());
                ps.setString(11,data.get(index).getContactoApellidos());
                ps.setString(12,data.get(index).getContacto_telefonoFijo());
                ps.setString(13,data.get(index).getContactoCelular());
                ps.setString(14,data.get(index).getReferidoPor());
                ps.setString(15,data.get(index).getId_suc());
                ps.setString(16,data.get(index).getCedula_pac());
                ps.executeUpdate();
                txtCiPacienteActualizar.requestFocus();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se actualizaron correctamente");
                a.showAndWait();
                tablaEditar.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        btnBuscarEliminar.setOnMouseClicked(mouseEvent -> {
            llenarEliminar();
            consultarInfoPaciente(txtCiPacienteActualizar.getText());
            tablaEliminar.setVisible(true);
            tablaEliminar.setItems(data);
        });

        btnEliminarEliminar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("" +
                        "delete from Paciente_Datos where cedula_pac=?;\n" +
                        "delete from Paciente_Cita where cedula_pac=?;" +
                        " commit");
                ps.setString(1, data.get(index).getCedula_pac());
                ps.setString(2, data.get(index).getCedula_pac());
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("El paciente: "+data.get(index).getNombre_pac()+", se elimino correctamente");
                data.remove(index);
                tablaEliminar.refresh();
                a.showAndWait();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


    }

    private void cargarSucursales(){
        cbSede.getItems().clear();
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT id_suc FROM SUCURSAL");
            rs = ps.executeQuery();
            while (rs.next()){
                cbSede.getItems().add(rs.getString(1));
            }
        } catch ( SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    private void cargarProvincias(){
        cbProvincia.getItems().clear();
        try{
            PreparedStatement ps;
            ResultSet rs;

            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT nombre FROM PROVINCIA");
            rs = ps.executeQuery();
            while (rs.next()){
                cbProvincia.getItems().add(rs.getString(1));
            }
        } catch ( SQLException ex) {
            System.out.println(ex.toString());
        }

        tablaEditar.setOnMouseClicked(mouseEvent -> {
            index = tablaEditar.getSelectionModel().getFocusedIndex();
        });

        tablaEliminar.setOnMouseClicked(mouseEvent -> {
            index = tablaEliminar.getSelectionModel().getFocusedIndex();
        });
    }

    private void cargarSexo(){
        cbSexoPaciente.getItems().clear();
        cbSexoPaciente.getItems().add("M");
        cbSexoPaciente.getItems().add("F");
    }

    private void cargarEstado(){
        cbEstadoPaciente.getItems().clear();
        cbEstadoPaciente.getItems().add("activo");
        cbEstadoPaciente.getItems().add("pasivo");
    }

    private void limpiarGuiRegistrar(){
        cbSede.getItems().clear();
        txtCedula.setText("");
        txtNombresPaciente.setText("");
        txtApellidosPaciente.setText("");
        txtFechaPaciente.setValue(null);
        cbSexoPaciente.getItems().clear();
        cbProvincia.getSelectionModel().clearSelection();
        cbCanton.getItems().clear();
        txtDireccionPaciente.setText("");
        cbEstadoPaciente.getItems().clear();
        txtCelularPaciente.setText("");
        txtTelefonoPaciente.setText("");
        txtCorreoPaciente.setText("");
        txtNombresContacto.setText("");
        txtApellidosContacto.setText("");
        txtTelefonoContacto.setText("");
        txtCeularContacto.setText("");
        txtReferido.setText("");
    }

    public void llenarColumnas(){
        //BORRAMOS LA INFORMACION DE LAS TABLAS
        tablaDatos.getItems().clear();
        tablaDatos.getColumns().clear();
        data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanPaciente, String> colCiPaciente = new TableColumn<>("Cédula");
        colCiPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("cedula_pac"));
        colCiPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colCiPaciente.setOnEditCommit(data ->{
            data.getRowValue().setCedula_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colNombrePaciente = new TableColumn<>("Nombres");
        colNombrePaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("nombre_pac"));
        colNombrePaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombrePaciente.setOnEditCommit(data ->{
            data.getRowValue().setNombre_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colApellidoPaciente = new TableColumn<>("Apellidos");
        colApellidoPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("apellidos_pac"));
        colApellidoPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidoPaciente.setOnEditCommit(data ->{
            data.getRowValue().setApellidos_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, Integer> colEdadPaciente = new TableColumn<>("Edad");
        colEdadPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, Integer>("edad_pac"));
        colEdadPaciente.setOnEditCommit(data ->{
            data.getRowValue().setEdad_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colFechaNacPaciente = new TableColumn<>("Fecha Nac.");
        colFechaNacPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("fechaNacimiento_pac"));
        colFechaNacPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colFechaNacPaciente.setOnEditCommit(data ->{
            data.getRowValue().setFechaNacimiento_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colDirPaciente = new TableColumn<>("Direcicón");
        colDirPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("direccion_pac"));
        colDirPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirPaciente.setOnEditCommit(data ->{
            data.getRowValue().setDireccion_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colCantonPac = new TableColumn<>("Cantón");
        colCantonPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("canton_pac"));
        colCantonPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colCantonPac.setOnEditCommit(data ->{
            data.getRowValue().setCanton_pac(data.getNewValue());
        });


        TableColumn<beanPaciente, String> colTlfPac = new TableColumn<>("Tlf. Fijo");
        colTlfPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("telefonoFijo_pac"));
        colTlfPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfPac.setOnEditCommit(data ->{
            data.getRowValue().setTelefonoFijo_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colTlfCellPac = new TableColumn<>("Tlf. Celular");
        colTlfCellPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("celular_pac"));
        colTlfCellPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfCellPac.setOnEditCommit(data ->{
            data.getRowValue().setCelular_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colMailPac = new TableColumn<>("Correo");
        colMailPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("correoElectronico_pac"));
        colMailPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colMailPac.setOnEditCommit(data ->{
            data.getRowValue().setCorreoElectronico_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colSexoPac = new TableColumn<>("Sexo");
        colSexoPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("sexo_pac"));
        colSexoPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colSexoPac.setOnEditCommit(data ->{
            data.getRowValue().setSexo_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("estado_pac"));
        colEstado.setCellFactory(TextFieldTableCell.forTableColumn());
        colEstado.setOnEditCommit(data ->{
            data.getRowValue().setEstado_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colContNombre = new TableColumn<>("Con. Nombre");
        colContNombre.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoNombres"));
        colContNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colContNombre.setOnEditCommit(data ->{
            data.getRowValue().setContactoNombres(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colContApellido = new TableColumn<>("Con. Apellido");
        colContApellido.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoApellidos"));
        colContApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        colContApellido.setOnEditCommit(data ->{
            data.getRowValue().setContactoApellidos(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colConTlfFijo = new TableColumn<>("Con. Tlf. Fijo");
        colConTlfFijo.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contacto_telefonoFijo"));
        colConTlfFijo.setCellFactory(TextFieldTableCell.forTableColumn());
        colConTlfFijo.setOnEditCommit(data ->{
            data.getRowValue().setContacto_telefonoFijo(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colConCelular = new TableColumn<>("Con. Tlf. Cell");
        colConCelular.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoCelular"));
        colConCelular.setCellFactory(TextFieldTableCell.forTableColumn());
        colConCelular.setOnEditCommit(data ->{
            data.getRowValue().setContactoCelular(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colRefPor = new TableColumn<>("Referido");
        colRefPor.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("referidoPor"));
        colRefPor.setCellFactory(TextFieldTableCell.forTableColumn());
        colRefPor.setOnEditCommit(data ->{
            data.getRowValue().setReferidoPor(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colIdSuc = new TableColumn<>("Suc.");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        tablaDatos.getColumns().addAll(colCiPaciente,
                colNombrePaciente,
                colApellidoPaciente,
                colEdadPaciente,
                colFechaNacPaciente,
                colDirPaciente,
                colCantonPac,
                colTlfPac,
                colTlfCellPac,
                colMailPac,
                colSexoPac,
                colEstado,
                colContNombre,
                colContApellido,
                colConTlfFijo,
                colConCelular,
                colRefPor,
                colIdSuc);
    }
    public void llenarColumnasEditar(){
        //ELIMINAMOS LA INFORMACION DE LA TABLA
        tablaEditar.getItems().clear();
        tablaEditar.getColumns().clear();
        this.data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanPaciente, String> colCiPaciente = new TableColumn<>("Cédula");
        colCiPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("cedula_pac"));
        colCiPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colCiPaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setCedula_pac(data.getNewValue());
        });




        TableColumn<beanPaciente, String> colNombrePaciente = new TableColumn<>("Nombres");
        colNombrePaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("nombre_pac"));
        colNombrePaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombrePaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setNombre_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colApellidoPaciente = new TableColumn<>("Apellidos");
        colApellidoPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("apellidos_pac"));
        colApellidoPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidoPaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setApellidos_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, Integer> colEdadPaciente = new TableColumn<>("Edad");
        colEdadPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, Integer>("edad_pac"));
        colEdadPaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setEdad_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colFechaNacPaciente = new TableColumn<>("Fecha Nac.");
        colFechaNacPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("fechaNacimiento_pac"));
        colFechaNacPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colFechaNacPaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setFechaNacimiento_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colDirPaciente = new TableColumn<>("Direcicón");
        colDirPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("direccion_pac"));
        colDirPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirPaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setDireccion_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colCantonPac = new TableColumn<>("Cantón");
        colCantonPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("canton_pac"));
        colCantonPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colCantonPac.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setCanton_pac(data.getNewValue());
        });


        TableColumn<beanPaciente, String> colTlfPac = new TableColumn<>("Tlf. Fijo");
        colTlfPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("telefonoFijo_pac"));
        colTlfPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfPac.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setTelefonoFijo_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colTlfCellPac = new TableColumn<>("Tlf. Celular");
        colTlfCellPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("celular_pac"));
        colTlfCellPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfCellPac.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setCelular_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colMailPac = new TableColumn<>("Correo");
        colMailPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("correoElectronico_pac"));
        colMailPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colMailPac.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setCorreoElectronico_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colSexoPac = new TableColumn<>("Sexo");
        colSexoPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("sexo_pac"));
        colSexoPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colSexoPac.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setSexo_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("estado_pac"));
        colEstado.setCellFactory(TextFieldTableCell.forTableColumn());
        colEstado.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setEstado_pac(data.getNewValue());
        });


        TableColumn<beanPaciente, String> colContNombre = new TableColumn<>("Con. Nombre");
        colContNombre.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoNombres"));
        colContNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colContNombre.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setContactoNombres(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colContApellido = new TableColumn<>("Con. Apellido");
        colContApellido.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoApellidos"));
        colContApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        colContApellido.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setContactoApellidos(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colConTlfFijo = new TableColumn<>("Con. Tlf. Fijo");
        colConTlfFijo.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contacto_telefonoFijo"));
        colConTlfFijo.setCellFactory(TextFieldTableCell.forTableColumn());
        colConTlfFijo.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setContacto_telefonoFijo(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colConCelular = new TableColumn<>("Con. Tlf. Cell");
        colConCelular.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoCelular"));
        colConCelular.setCellFactory(TextFieldTableCell.forTableColumn());
        colConCelular.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setContactoCelular(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colRefPor = new TableColumn<>("Referido");
        colRefPor.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("referidoPor"));
        colRefPor.setCellFactory(TextFieldTableCell.forTableColumn());
        colRefPor.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setReferidoPor(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colIdSuc = new TableColumn<>("Suc.");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setId_suc(data.getNewValue());
        });

        tablaEditar.getColumns().addAll(colCiPaciente,
                colNombrePaciente,
                colApellidoPaciente,
                colEdadPaciente,
                colFechaNacPaciente,
                colDirPaciente,
                colCantonPac,
                colTlfPac,
                colTlfCellPac,
                colMailPac,
                colSexoPac,
                colEstado,
                colContNombre,
                colContApellido,
                colConTlfFijo,
                colConCelular,
                colRefPor,
                colIdSuc);
    }

    private void consultarInfoPaciente(String numCiPaciente){
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            if(numCiPaciente.equals("")){
                ps = con.prepareStatement("select * from Paciente_Info");
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanPaciente (
                            rs.getString("cedula_pac"),
                            rs.getString("nombre_pac"),
                            rs.getString("apellidos_pac"),
                            rs.getInt("edad_pac"),
                            rs.getString("fechaNacimiento_pac"),
                            rs.getString("direccion_pac"),
                            rs.getString("canton_pac"),
                            rs.getString("telefonoFijo_pac"),
                            rs.getString("celular_pac"),
                            rs.getString("correoElectronico_pac"),
                            rs.getString("sexo_pac"),
                            rs.getString("estado_pac"),
                            rs.getString("contactoNombres"),
                            rs.getString("contactoApellidos"),
                            rs.getString("contacto_telefonoFijo"),
                            rs.getString("contactoCelular"),
                            rs.getString("referidoPor"),
                            rs.getString("id_suc")
                    ));

                }
            }else{
                String where = " WHERE cedula_pac=?";

                ps = con.prepareStatement("select * from Paciente_Info"+where);
                ps.setString(1, numCiPaciente);
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanPaciente (
                            rs.getString("cedula_pac"),
                            rs.getString("nombre_pac"),
                            rs.getString("apellidos_pac"),
                            rs.getInt("edad_pac"),
                            rs.getString("fechaNacimiento_pac"),
                            rs.getString("direccion_pac"),
                            rs.getString("canton_pac"),
                            rs.getString("telefonoFijo_pac"),
                            rs.getString("celular_pac"),
                            rs.getString("correoElectronico_pac"),
                            rs.getString("sexo_pac"),
                            rs.getString("estado_pac"),
                            rs.getString("contactoNombres"),
                            rs.getString("contactoApellidos"),
                            rs.getString("contacto_telefonoFijo"),
                            rs.getString("contactoCelular"),
                            rs.getString("referidoPor"),
                            rs.getString("id_suc")
                    ));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void llenarEliminar(){
        //BORRAMOS LA INFORMACION DE LAS TABLAS
        tablaEliminar.getItems().clear();
        tablaEliminar.getColumns().clear();
        data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanPaciente, String> colCiPaciente = new TableColumn<>("Cédula");
        colCiPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("cedula_pac"));
        colCiPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colCiPaciente.setOnEditCommit(data ->{
            data.getRowValue().setCedula_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colNombrePaciente = new TableColumn<>("Nombres");
        colNombrePaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("nombre_pac"));
        colNombrePaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombrePaciente.setOnEditCommit(data ->{
            data.getRowValue().setNombre_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colApellidoPaciente = new TableColumn<>("Apellidos");
        colApellidoPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("apellidos_pac"));
        colApellidoPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidoPaciente.setOnEditCommit(data ->{
            data.getRowValue().setApellidos_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, Integer> colEdadPaciente = new TableColumn<>("Edad");
        colEdadPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, Integer>("edad_pac"));
        colEdadPaciente.setOnEditCommit(data ->{
            data.getRowValue().setEdad_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colFechaNacPaciente = new TableColumn<>("Fecha Nac.");
        colFechaNacPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("fechaNacimiento_pac"));
        colFechaNacPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colFechaNacPaciente.setOnEditCommit(data ->{
            data.getRowValue().setFechaNacimiento_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colDirPaciente = new TableColumn<>("Direcicón");
        colDirPaciente.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("direccion_pac"));
        colDirPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirPaciente.setOnEditCommit(data ->{
            data.getRowValue().setDireccion_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colCantonPac = new TableColumn<>("Cantón");
        colCantonPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("canton_pac"));
        colCantonPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colCantonPac.setOnEditCommit(data ->{
            data.getRowValue().setCanton_pac(data.getNewValue());
        });


        TableColumn<beanPaciente, String> colTlfPac = new TableColumn<>("Tlf. Fijo");
        colTlfPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("telefonoFijo_pac"));
        colTlfPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfPac.setOnEditCommit(data ->{
            data.getRowValue().setTelefonoFijo_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colTlfCellPac = new TableColumn<>("Tlf. Celular");
        colTlfCellPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("celular_pac"));
        colTlfCellPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfCellPac.setOnEditCommit(data ->{
            data.getRowValue().setCelular_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colMailPac = new TableColumn<>("Correo");
        colMailPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("correoElectronico_pac"));
        colMailPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colMailPac.setOnEditCommit(data ->{
            data.getRowValue().setCorreoElectronico_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colSexoPac = new TableColumn<>("Sexo");
        colSexoPac.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("sexo_pac"));
        colSexoPac.setCellFactory(TextFieldTableCell.forTableColumn());
        colSexoPac.setOnEditCommit(data ->{
            data.getRowValue().setSexo_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("estado_pac"));
        colEstado.setCellFactory(TextFieldTableCell.forTableColumn());
        colEstado.setOnEditCommit(data ->{
            data.getRowValue().setEstado_pac(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colContNombre = new TableColumn<>("Con. Nombre");
        colContNombre.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoNombres"));
        colContNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colContNombre.setOnEditCommit(data ->{
            data.getRowValue().setContactoNombres(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colContApellido = new TableColumn<>("Con. Apellido");
        colContApellido.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoApellidos"));
        colContApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        colContApellido.setOnEditCommit(data ->{
            data.getRowValue().setContactoApellidos(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colConTlfFijo = new TableColumn<>("Con. Tlf. Fijo");
        colConTlfFijo.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contacto_telefonoFijo"));
        colConTlfFijo.setCellFactory(TextFieldTableCell.forTableColumn());
        colConTlfFijo.setOnEditCommit(data ->{
            data.getRowValue().setContacto_telefonoFijo(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colConCelular = new TableColumn<>("Con. Tlf. Cell");
        colConCelular.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("contactoCelular"));
        colConCelular.setCellFactory(TextFieldTableCell.forTableColumn());
        colConCelular.setOnEditCommit(data ->{
            data.getRowValue().setContactoCelular(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colRefPor = new TableColumn<>("Referido");
        colRefPor.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("referidoPor"));
        colRefPor.setCellFactory(TextFieldTableCell.forTableColumn());
        colRefPor.setOnEditCommit(data ->{
            data.getRowValue().setReferidoPor(data.getNewValue());
        });

        TableColumn<beanPaciente, String> colIdSuc = new TableColumn<>("Suc.");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanPaciente, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        tablaEliminar.getColumns().addAll(colCiPaciente,
                colNombrePaciente,
                colApellidoPaciente,
                colEdadPaciente,
                colFechaNacPaciente,
                colDirPaciente,
                colCantonPac,
                colTlfPac,
                colTlfCellPac,
                colMailPac,
                colSexoPac,
                colEstado,
                colContNombre,
                colContApellido,
                colConTlfFijo,
                colConCelular,
                colRefPor,
                colIdSuc);
    }
}
