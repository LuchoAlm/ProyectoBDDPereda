package sample.controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeTableColumn;
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
    ////            LOGICA DEL PANEL REGISTRAR                         ////
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
        ////       FIN LOGICA PARA REGRESAR A CUALQUIERA DE LAS VENTANAS    ////
        ////////////////////////////////////////////////////////////////////////


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
        });

        btnActualizarPaciente.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneActualizarPaciente.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnEliminarPaciente.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarPaciente.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
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
            beanPaciente newPac = new beanPaciente(
                    txtCedula.getText(),
                    txtNombresPaciente.getText(),
                    txtApellidosPaciente.getText(),
                    28,
                    txtFechaPaciente.getValue().format(formatter),
                    txtDireccionPaciente.getText(),
                    cbCanton.getValue().toString(),
                    cbProvincia.getValue().toString(),
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
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se registraron correctamente");
                a.setContentText("Se agregó la sucursal: " + txtNombresPaciente.getText());
                a.showAndWait();
                //Ocultamos el panel
                //paneRegistroPaciente.setVisible(false);
                //paneOpciones.setVisible(true);
                //regresarOpciones.setVisible(false);
                //regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });



    }

    private void cargarSucursales(){
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
    }

    private void cargarSexo(){
        cbSexoPaciente.getItems().add("M");
        cbSexoPaciente.getItems().add("F");
    }

    private void cargarEstado(){
        cbEstadoPaciente.getItems().add("activo");
        cbEstadoPaciente.getItems().add("pasivo");
    }
}
