package sample.controlador;

import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.Initializable;
import sample.modelo.Conexion;
import sample.modelo.beanSucursal;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class controllerSucursal implements Initializable {
    double x=0, y=0;
    private ObservableList<beanSucursal> data = FXCollections.observableArrayList();

    //  BOTONES BARRA SUPERIOR
    @FXML
    private FontIcon exit, menu, minimice, regresar;

    //  ICONOS MENU LATERAL
    @FXML
    private FontIcon icoSucursal, icoPaciente, icoOdontologo, icoCita, icoHistoriaClinica, icoCatalogo;

    //  PANEL BOTONES
    @FXML
    private AnchorPane pane1, pane2;

    //  BONES PANEL BOTONES
    @FXML
    private JFXButton btnSucursal, btnPaciente, btnOdontologo, btnCita, btnHsitoriaClinica, btnCatalogo, btnRegresar;

    //  ELEMENTOS DE LA VENTA
    @FXML
    private FontIcon regresarOpciones;
    @FXML
    private AnchorPane paneOpciones, paneRegistroSucursal, paneConsultarSucursal, paneActualizarSucursal, paneEliminarSucursal;
    @FXML
    private JFXButton btnRegistrarSucursal, btnActualizarSucursal, btnConsultarSucursal, btnEliminarSucursal;


    //////////////////////////////////////////////////////////////////////
    ////        Panel Registro Sucursal                               ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdSuc, txtNombreSuc, txtDirSuc, txtTlfSuc;
    @FXML
    private JFXButton btnRegistrarSuc, btnCancelarRegistro;


    //////////////////////////////////////////////////////////////////////
    ////        Panel Buscar Sucursal                                 ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<beanSucursal> tablaDatos;

    @FXML
    private JFXButton btnBuscarConsultar, btnCancelarConsultar;

    @FXML
    private JFXTextField txtIdBuscar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Actualizar Sucursal                             ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<beanSucursal> tablaEditar;
    private int index;
    @FXML
    private JFXButton btnActualizarActualizar;

    private String id_suc, nombre_suc, direccion_suc, tlf_suc;
    @FXML
    private JFXTextField txtCodigoSucursalBuscar1;



    @FXML
    private JFXButton btnBuscarActualizar, btnCancelarActualizar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Eliminar Sucursal                               ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<beanSucursal> tablaEliminar;

    @FXML
    private JFXButton btnBuscarEliminar, btnCancelarEliminar, btnEliminarEliminar;

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

        //Para regresar al Login Principal
        regresar.setOnMouseClicked(mouseEvent -> {
           regresarLogin(mouseEvent);
        });

        btnRegresar.setOnMouseClicked(mouseEvent -> {
            regresarLogin(mouseEvent);
        });

        //////////////////////////////////////////////////////////////////////
        ////        Logica para desplazar menús                           ////
        //////////////////////////////////////////////////////////////////////
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
            paneActualizarSucursal.setVisible(false);
            paneEliminarSucursal.setVisible(false);
            paneConsultarSucursal.setVisible(false);
            paneRegistroSucursal.setVisible(false);
            paneOpciones.setVisible(true);
            regresarOpciones.setVisible(false);
            regresar.setVisible(true);
        });

        btnRegistrarSucursal.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneRegistroSucursal.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnConsultarSucursal.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneConsultarSucursal.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            //CARGAR LA TABLA DE CONSULTAR SUCURSAL
        });

        btnActualizarSucursal.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneActualizarSucursal.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            //CARGAR LA TABLA DE ACTUALIZAR SUCURSAL
        });

        btnEliminarSucursal.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarSucursal.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            //CARGAR TABLA PARA ELIMINAR SUCURSAL
        });



        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PANEL REGISTRAR SUCURSAL                     ////
        ////////////////////////////////////////////////////////////////////////

        btnRegistrarSuc.setOnMouseClicked(mouseEvent -> {
            beanSucursal newSuc = new beanSucursal(
                    txtIdSuc.getText().toUpperCase(),
                    txtNombreSuc.getText(),
                    txtDirSuc.getText(),
                    txtTlfSuc.getText());

            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("INSERT INTO SUCURSAL VALUES (?,?,?,?, NEWID())");
                ps.setString(1, newSuc.getId_suc());
                ps.setString(2, newSuc.getNombre_suc());
                ps.setString(3, newSuc.getDireccion_suc());
                ps.setString(4, newSuc.getTelefono_suc());
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se registraron correctamente");
                a.setContentText("Se agregó la sucursal: " + txtNombreSuc.getText());
                a.showAndWait();
                //Ocultamos el panel
                limpiarPaneRegistrar();
                paneRegistroSucursal.setVisible(false);
                paneOpciones.setVisible(true);
                regresarOpciones.setVisible(false);
                regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

         btnCancelarRegistro.setOnMouseClicked(mouseEvent -> {
            limpiarPaneRegistrar();
            txtIdSuc.requestFocus();
        });


        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PANEL CONSULTAR SUCURSAL                     ////
        ////////////////////////////////////////////////////////////////////////
        btnBuscarConsultar.setOnMouseClicked(mouseEvent -> {
            llenarColumnas();
            try{
                PreparedStatement ps;
                ResultSet rs;
                Connection con = Conexion.getConexion();
                if(txtIdBuscar.getText().trim().isEmpty()){
                    ps = con.prepareStatement("SELECT * FROM SUCURSAL");
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanSucursal (
                                rs.getString("id_suc"),
                                rs.getString("nombre_suc"),
                                rs.getString("direccion_suc"),
                                rs.getString("telefono_suc")
                        ));
                        tablaDatos.setItems(data);
                    }
                }else{
                    String where = " WHERE id_suc=?";
                    ps = con.prepareStatement("SELECT * FROM SUCURSAL"+where);
                    ps.setString(1, txtIdBuscar.getText());
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanSucursal (
                                rs.getString("id_suc"),
                                rs.getString("nombre_suc"),
                                rs.getString("direccion_suc"),
                                rs.getString("telefono_suc")
                        ));
                        tablaDatos.setItems(data);
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        });


        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PANEL ACTUALIZAR SUCURSAL                    ////
        ////////////////////////////////////////////////////////////////////////
        btnBuscarActualizar.setOnMouseClicked(mouseEvent -> {
           llenarColumnasEditar();
           tablaEditar.setEditable(true);
            try{
                PreparedStatement ps;
                ResultSet rs;
                Connection con = Conexion.getConexion();
                if(txtCodigoSucursalBuscar1.getText().trim().isEmpty()){
                    ps = con.prepareStatement("SELECT * FROM SUCURSAL");
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanSucursal (
                                rs.getString("id_suc"),
                                rs.getString("nombre_suc"),
                                rs.getString("direccion_suc"),
                                rs.getString("telefono_suc")
                        ));
                        tablaEditar.setItems(data);
                    }
                }else{
                    String where = " WHERE id_suc=?";
                    ps = con.prepareStatement("SELECT * FROM SUCURSAL"+where);
                    ps.setString(1, txtCodigoSucursalBuscar1.getText());
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanSucursal (
                                rs.getString("id_suc"),
                                rs.getString("nombre_suc"),
                                rs.getString("direccion_suc"),
                                rs.getString("telefono_suc")
                        ));
                        tablaEditar.setItems(data);
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        });

        tablaEditar.setOnMouseClicked(mouseEvent -> {
            //OBTENGO LA EL INDICE DE LA TABLA
            index = tablaEditar.getSelectionModel().getFocusedIndex();
            //OBTENGO EL CAMPO DEL INDICE ESPECIFICO
            id_suc = tablaEditar.getItems().get(index).getId_suc();
            nombre_suc =tablaEditar.getItems().get(index).getNombre_suc();
            direccion_suc= tablaEditar.getItems().get(index).getDireccion_suc();
            tlf_suc = tablaEditar.getItems().get(index).getTelefono_suc();
        });

        btnActualizarActualizar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("UPDATE SUCURSAL SET id_suc=?, nombre_suc=?, " +
                        "direccion_suc=?, telefono_suc=? WHERE id_suc =?");
                ps.setString(1, id_suc);
                ps.setString(2, nombre_suc);
                ps.setString(3, direccion_suc);
                ps.setString(4, tlf_suc);
                ps.setString(5, id_suc);
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se actualizaron correctamente");
                data.get(index).setId_suc(id_suc);
                data.get(index).setNombre_suc(nombre_suc);
                data.get(index).setDireccion_suc(direccion_suc);
                data.get(index).setTelefono_suc(tlf_suc);
                tablaEditar.refresh();

                a.showAndWait();
                //Ocultamos el panel
                //paneRegistroSucursal.setVisible(false);
                //paneOpciones.setVisible(true);
                //regresarOpciones.setVisible(false);
                //regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PANEL ELIMINAR SUCURSAL                      ////
        ////////////////////////////////////////////////////////////////////////
        btnBuscarEliminar.setOnMouseClicked(mouseEvent -> {
            llenarColumnasBorrar();
            try{
                PreparedStatement ps;
                ResultSet rs;

                Connection con = Conexion.getConexion();
                ps = con.prepareStatement("SELECT * FROM SUCURSAL");
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanSucursal (
                            rs.getString("id_suc"),
                            rs.getString("nombre_suc"),
                            rs.getString("direccion_suc"),
                            rs.getString("telefono_suc")
                    ));
                    tablaEliminar.setItems(data);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        });

        tablaEliminar.setOnMouseClicked(mouseEvent -> {
            //OBTENGO LA EL INDICE DE LA TABLA
            index = tablaEliminar.getSelectionModel().getFocusedIndex();
            //OBTENGO EL CAMPO DEL INDICE ESPECIFICO
            id_suc = tablaEliminar.getItems().get(index).getId_suc();
        });

        btnEliminarEliminar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("DELETE FROM SUCURSAL WHERE id_suc =?");
                ps.setString(1, id_suc);
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se borraron correctamente");
                //a.setContentText("El paciente: NOMBRE_APELLIDO se registró en la base de datos");
                data.remove(index);
                tablaEliminar.refresh();
                a.showAndWait();

                //Ocultamos el panel
                //paneRegistroSucursal.setVisible(false);
                //paneOpciones.setVisible(true);
                //regresarOpciones.setVisible(false);
                //regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });


    }



    private void regresarLogin(Event mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/menuPrincipal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            root.setOnMousePressed(event ->{
                x=event.getSceneX();
                y= event.getSceneY();
            });
            root.setOnMouseDragged(event ->{
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
            Stage s;
            s = (Stage) ((FontIcon) mouseEvent.getSource()).getScene().getWindow();
            s.close();
        } catch (IOException e) {
            System.out.println("Error al cargar de Login");
        }
    }

    //Metodo para limpiar la interfaz de registro
    private void limpiarPaneRegistrar(){
        txtIdSuc.setText("");
        txtNombreSuc.setText("");
        txtDirSuc.setText("");
        txtTlfSuc.setText("");
    }


    public void llenarColumnas(){
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanSucursal, String> colIdSuc = new TableColumn<>("ID Suc");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        TableColumn<beanSucursal, String> colNombre = new TableColumn<>("Nombre Sucursal");
        colNombre.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("nombre_suc"));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombre.setOnEditCommit(data ->{
            data.getRowValue().setNombre_suc(data.getNewValue());
        });

        TableColumn<beanSucursal, String> colDirSuc = new TableColumn<>("ID Suc");
        colDirSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("direccion_suc"));
        colDirSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        TableColumn<beanSucursal, String> colTlfSuc = new TableColumn<>("Tlf Suc");
        colTlfSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("telefono_suc"));
        colTlfSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        tablaDatos.getColumns().addAll(colIdSuc, colNombre, colDirSuc, colTlfSuc);
    }

    public void llenarColumnasEditar(){
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanSucursal, String> colIdSuc = new TableColumn<>("ID Suc");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            id_suc=data.getNewValue();
        });

        TableColumn<beanSucursal, String> colNombre = new TableColumn<>("Nombre Sucursal");
        colNombre.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("nombre_suc"));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombre.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            nombre_suc=data.getNewValue();
        });

        TableColumn<beanSucursal, String> colDirSuc = new TableColumn<>("ID Suc");
        colDirSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("direccion_suc"));
        colDirSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            direccion_suc=data.getNewValue();
        });

        TableColumn<beanSucursal, String> colTlfSuc = new TableColumn<>("Tlf Suc");
        colTlfSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("telefono_suc"));
        colTlfSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            tlf_suc=data.getNewValue();
        });

        tablaEditar.getColumns().addAll(colIdSuc, colNombre, colDirSuc, colTlfSuc);
    }

    public void llenarColumnasBorrar(){
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanSucursal, String> colIdSuc = new TableColumn<>("ID Suc");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<beanSucursal, String> colNombre = new TableColumn<>("Nombre Sucursal");
        colNombre.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("nombre_suc"));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<beanSucursal, String> colDirSuc = new TableColumn<>("ID Suc");
        colDirSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("direccion_suc"));
        colDirSuc.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<beanSucursal, String> colTlfSuc = new TableColumn<>("Tlf Suc");
        colTlfSuc.setCellValueFactory(new PropertyValueFactory<beanSucursal, String>("telefono_suc"));
        colTlfSuc.setCellFactory(TextFieldTableCell.forTableColumn());

        tablaEliminar.getColumns().addAll(colIdSuc, colNombre, colDirSuc, colTlfSuc);
    }





}
