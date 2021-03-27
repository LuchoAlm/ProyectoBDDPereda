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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import sample.modelo.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class controllerCita implements Initializable {
    double x=0, y=0;
    private ObservableList<beanCita> data = FXCollections.observableArrayList();
    private ObservableList<String> paciente = FXCollections.observableArrayList();
    private ObservableList<String> odontologo = FXCollections.observableArrayList();
    private ObservableList<String> tratamiento = FXCollections.observableArrayList();
    int indexP, indexO, indexT;
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
    private AnchorPane paneOpciones, paneRegistrarAgenda, paneConsultarCita, paneEliminarCita;
    @FXML
    private JFXButton btnRegistrarCita,btnConsultarCita, btnEliminarCita;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Registro Cita                                   ////
    //////////////////////////////////////////////////////////////////////

    @FXML
    private JFXTextField txtIdCita, txtHoraCita, txtHclinica;

    @FXML
    private JFXComboBox cbSede, cbPaciente, cbOdontologo, cbTratamiento;

    @FXML
    private DatePicker txtFechaCita;

    @FXML
    private JFXButton btnRegistrar, btnCancelarRegistrar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Consultar Cita                                  ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdCitaBuscar;

    @FXML
    private JFXButton btnBuscarCita, btnCancelarBuscar;

    @FXML
    private TableView<beanCita> tablaDatos;




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

        btnCita.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/cita.fxml"));
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
                System.out.println("Error al cargar la ventana de cita");
            }
        });

        icoCita.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/cita.fxml"));
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
                System.out.println("Error al cargar la ventana de cita");
            }
        });

        btnHsitoriaClinica.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/hcl.fxml"));
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
                System.out.println("Error al cargar la ventana de Historia Clínica");
            }
        });

        icoHistoriaClinica.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/hcl.fxml"));
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
                System.out.println("Error al cargar la ventana de historia Clínica");
            }
        });

        btnCatalogo.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/catalogo.fxml"));
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
                System.out.println("Error al cargar la ventana de catalogo");
            }
        });

        icoCatalogo.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/catalogo.fxml"));
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
                System.out.println("Error al cargar la ventana de catalogo");
            }
        });


        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA OCULTAR PANELES                         ////
        ////////////////////////////////////////////////////////////////////////
        regresarOpciones.setOnMouseClicked(mouseEvent -> {
            paneEliminarCita.setVisible(false);
            paneConsultarCita.setVisible(false);
            paneRegistrarAgenda.setVisible(false);
            paneOpciones.setVisible(true);
            regresarOpciones.setVisible(false);
            regresar.setVisible(true);
        });

        btnRegistrarCita.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneRegistrarAgenda.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            cargarSucursales();
            cargarTratamientos();
        });

        btnConsultarCita.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneConsultarCita.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaDatos.setVisible(false);
            tablaDatos.getItems().clear();
            tablaDatos.getColumns().clear();
            data.clear();
        });


        btnEliminarCita.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarCita.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA REGISTRAR CITA                          ////
        ////////////////////////////////////////////////////////////////////////

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtFechaCita.setConverter(new LocalDateStringConverter(formatter,null));

        txtFechaCita.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    System.out.println(txtFechaCita.getValue().format(formatter));
                    System.out.println(txtFechaCita.getValue().format(formatter));
                }
            }
        });

        cbSede.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    cargarOdontologos();
                    cargarPacientes();
                }
            }
        });

        cbPaciente.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    indexP=cbPaciente.getSelectionModel().getSelectedIndex();
                }
            }
        });

        cbOdontologo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    indexO=cbOdontologo.getSelectionModel().getSelectedIndex();
                }
            }
        });

        cbTratamiento.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    indexT=cbTratamiento.getSelectionModel().getSelectedIndex();
                }
            }
        });

        btnRegistrar.setOnMouseClicked(mouseEvent -> {
            beanCita newCita = new beanCita(
                    txtIdCita.getText(),
                    txtFechaCita.getValue().format(formatter),
                    txtHoraCita.getText(),
                    cbSede.getValue().toString(),
                    paciente.get(indexP).toString(),
                    odontologo.get(indexO).toString(),
                    tratamiento.get(indexT).toString(),
                    txtHclinica.getText());
            try {
                if(citaDisponible(newCita.getFecha_cita(), newCita.getHora_cita(), newCita.getCedula_odontologo())){
                    //CITA_SUC
                    Connection con = Conexion.getConexion();
                    PreparedStatement ps = con.prepareStatement("SET XACT_ABORT ON; " +
                            "begin distributed tran INSERT INTO CitaPD VALUES (?,?,?,?,?,?,?,?);" +
                            " commit");
                    ps.setString(1,newCita.getId_cita());
                    ps.setString(2,newCita.getFecha_cita());
                    ps.setString(3,newCita.getHora_cita());
                    ps.setString(4,newCita.getId_sucursal());
                    ps.setString(5,newCita.getCedula_paciente());
                    ps.setString(6,newCita.getCedula_odontologo());
                    ps.setString(7,newCita.getId_tratamiento());
                    ps.setString(8,newCita.getId_hclinica());
                    ps.executeUpdate();

                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Los datos se registraron correctamente");
                    a.setContentText("Se registró una cita para el: " + newCita.getFecha_cita()+
                            ",\na las: " + newCita.getHora_cita()+
                            "\nCon el doctor: "+ cbOdontologo.getSelectionModel().getSelectedItem().toString());
                    a.showAndWait();
                    limpiarGuiRegistrar();
                    //Ocultamos el panel
                    paneRegistrarAgenda.setVisible(false);
                    paneOpciones.setVisible(true);
                    regresarOpciones.setVisible(false);
                    regresar.setVisible(true);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al Agendar Cita");
                    alert.setHeaderText("La fecha y hora no está disponible");
                    alert.setContentText("Ooops, no se puede agendar la cita para esa fecha y hora");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.showAndWait();
                    txtFechaCita.setValue(null);
                    txtHoraCita.setText("");
                    txtFechaCita.requestFocus();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA CONSULTAR CITA                          ////
        ////////////////////////////////////////////////////////////////////////

        btnBuscarCita.setOnMouseClicked(mouseEvent -> {
            llenarColumnas();
            cargarInfoCita(txtIdCitaBuscar.getText());
            tablaDatos.setVisible(true);
            tablaDatos.setItems(data);


        });



    }
    private void limpiarGuiRegistrar(){
        txtIdCita.setText("");
        txtFechaCita.setValue(null);
        txtHoraCita.setText("");
        cbSede.getSelectionModel().clearSelection();
        cbPaciente.getSelectionModel().clearSelection();
        cbOdontologo.getSelectionModel().clearSelection();
        cbTratamiento.getSelectionModel().clearSelection();
        txtHclinica.setText("");
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

    private void cargarPacientes(){
        cbPaciente.getItems().clear();
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("select (rtrim(nombre_pac)+' '+ apellidos_pac) as 'nombres_pac', cedula_pac from Paciente_Cita where id_suc=?");
            ps.setString(1, cbSede.getSelectionModel().getSelectedItem().toString());
            rs = ps.executeQuery();
            while (rs.next()){
                cbPaciente.getItems().add(rs.getString(1));
                paciente.add(rs.getString(2));
            }
        } catch ( SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private void cargarOdontologos(){
        cbOdontologo.getItems().clear();
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("select (rtrim(nombres_odon)+' '+ apellidos_odon) as 'nombres_odon', cedula_odon from Odontologo_Cita where id_suc=?");
            ps.setString(1,cbSede.getSelectionModel().getSelectedItem().toString());
            rs = ps.executeQuery();
            while (rs.next()){
                cbOdontologo.getItems().add(rs.getString(1));
                odontologo.add(rs.getString(2));
            }
        } catch ( SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private void cargarTratamientos(){
        cbTratamiento.getItems().clear();
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("select descripcion_trat, id_trat from TRATAMIENTO");
            rs = ps.executeQuery();
            while (rs.next()){
                cbTratamiento.getItems().add(rs.getString(1));
                tratamiento.add(rs.getString(2));
            }
        } catch ( SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private boolean citaDisponible(String fecha, String hora, String cedulaOdonto){
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("select * from CitaPD where fecha_cita=? and hora_cita=? and cedula_odontologo=?");
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setString(3,cedulaOdonto);
            rs = ps.executeQuery();
            if(!(rs.next())){
                return true;
            }else{
                return false;
            }
        } catch ( SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public void llenarColumnas(){
        //BORRAMOS LA INFORMACION DE LAS TABLAS
        tablaDatos.getItems().clear();
        tablaDatos.getColumns().clear();
        data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanCita, String> colFechaCita = new TableColumn<>("Fecha");
        colFechaCita.setCellValueFactory(new PropertyValueFactory<beanCita, String>("fecha_cita"));
        colFechaCita.setCellFactory(TextFieldTableCell.forTableColumn());
        colFechaCita.setOnEditCommit(data ->{
            data.getRowValue().setFecha_cita(data.getNewValue());
        });

        TableColumn<beanCita, String> colHoraCita = new TableColumn<>("Hora");
        colHoraCita.setCellValueFactory(new PropertyValueFactory<beanCita, String>("hora_cita"));
        colHoraCita.setCellFactory(TextFieldTableCell.forTableColumn());
        colHoraCita.setOnEditCommit(data ->{
            data.getRowValue().setHora_cita(data.getNewValue());
        });

        TableColumn<beanCita, String> colPacienteCita = new TableColumn<>("Paciente");
        colPacienteCita.setCellValueFactory(new PropertyValueFactory<beanCita, String>("cedula_paciente"));
        colPacienteCita.setCellFactory(TextFieldTableCell.forTableColumn());
        colPacienteCita.setOnEditCommit(data ->{
            data.getRowValue().setCedula_paciente(data.getNewValue());
        });

        TableColumn<beanCita, String> colOdontologoCita = new TableColumn<>("Odontólogo");
        colOdontologoCita.setCellValueFactory(new PropertyValueFactory<beanCita, String>("cedula_odontologo"));
        colOdontologoCita.setCellFactory(TextFieldTableCell.forTableColumn());
        colOdontologoCita.setOnEditCommit(data ->{
            data.getRowValue().setCedula_odontologo(data.getNewValue());
        });

        TableColumn<beanCita, String> colIdTra = new TableColumn<>("Tratamiento");
        colIdTra.setCellValueFactory(new PropertyValueFactory<beanCita, String>("id_tratamiento"));
        colIdTra.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdTra.setOnEditCommit(data ->{
            data.getRowValue().setId_tratamiento(data.getNewValue());
        });

        tablaDatos.getColumns().addAll(colFechaCita, colHoraCita, colPacienteCita, colOdontologoCita,colIdTra);
    }

    private void cargarInfoCita(String idCita){
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            if(idCita.equals("")){
                ps = con.prepareStatement("EXECUTE citasPD");
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanCita(
                            null,
                            rs.getString("fecha_cita"),
                            rs.getString("hora_cita"),
                            null,
                            rs.getString("cedula_paciente"),
                            rs.getString("cedula_odontologo"),
                            rs.getString("id_tratamiento"),
                            null
                    ));
                }
            }else{
                CallableStatement cst = con.prepareCall("{call citasPDW (?)}");
                cst.setString(1, idCita);

                cst.registerOutParameter("fecha_cita", Types.DATE);
                cst.registerOutParameter("hora_cita", Types.TIME);
                cst.registerOutParameter("cedula_paciente", Types.NVARCHAR);
                cst.registerOutParameter("cedula_odontologo", Types.NVARCHAR);
                cst.registerOutParameter("id_tratamiento", Types.NVARCHAR);

                cst.execute();

                data.add(new beanCita(
                        null,
                        cst.getString("fecha_cita"),
                        cst.getString("hora_cita"),
                        null,
                        cst.getString("cedula_paciente"),
                        cst.getString("cedula_odontologo"),
                        cst.getString("id_tratamiento"),
                        null
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}