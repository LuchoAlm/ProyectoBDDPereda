package sample.controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import com.jfoenix.controls.JFXButton;
import sample.modelo.Conexion;
import sample.modelo.beanOdontologo;
import sample.modelo.beanPaciente;
import sample.modelo.beanSucursal;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class controllerOdontologo implements Initializable {
    double x=0, y=0;
    private ObservableList<beanOdontologo> data = FXCollections.observableArrayList();
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
    private AnchorPane paneOpciones, paneRegistrarOdontologo, paneConsultarOdontologo, paneActualizarOdontologo, paneEliminarOdontologo;
    @FXML
    private JFXButton btnRegistrarOdontologo,btnConsultarOdontologo, btnActualizarOdontologo, btnEliminarOdontologo;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Registro Odontologo                             ////
    //////////////////////////////////////////////////////////////////////

    @FXML
    private JFXComboBox cbSede;

    @FXML
    private JFXTextField txtCedula, txtNombresOdontologo, txtApellidosOdontologo, txtEspecialidadOdontologo, txtTelefonoOdontologo, txtCelularOdontologo;

    @FXML
    private JFXButton btnRegistrar, btnCancelar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Buscar Odontologo                               ////
    //////////////////////////////////////////////////////////////////////

    @FXML
    private JFXTextField txtCiOdontologo;

    @FXML
    private JFXButton btnBuscarOdonto, btnBuscarCancelar;

    @FXML
    private TableView<beanOdontologo> tablaDatos;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Actualizar Odontologo                           ////
    //////////////////////////////////////////////////////////////////////

    @FXML
    private JFXTextField txtCiActualizar;

    @FXML
    private JFXButton btnBuscarActualizar, btnCancelarActualizar, btnActualizarActualizar;

    @FXML
    private TableView<beanOdontologo> tablaEditar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Eliminar Sucursal                               ////
    //////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<beanOdontologo> tablaEliminar;

    @FXML
    private JFXButton btnBuscarEliminar, btnCancelarEliminar, btnEliminarEliminar;

    @FXML
    private JFXTextField txtCiEliminar;

    int index;

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
            paneActualizarOdontologo.setVisible(false);
            paneEliminarOdontologo.setVisible(false);
            paneConsultarOdontologo.setVisible(false);
            paneRegistrarOdontologo.setVisible(false);
            paneOpciones.setVisible(true);
            regresarOpciones.setVisible(false);
            regresar.setVisible(true);
        });

        btnRegistrarOdontologo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneRegistrarOdontologo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            cargarSucursales();
        });

        btnConsultarOdontologo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneConsultarOdontologo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaDatos.setVisible(false);
            tablaDatos.getItems().clear();
            tablaDatos.getColumns().clear();
            this.data.clear();
        });

        btnActualizarOdontologo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneActualizarOdontologo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaEditar.setVisible(false);
            tablaEditar.getItems().clear();
            tablaEditar.getColumns().clear();
            this.data.clear();
        });

        btnEliminarOdontologo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarOdontologo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
            tablaEliminar.setVisible(false);
            tablaEliminar.getItems().clear();
            tablaEliminar.getColumns().clear();
            this.data.clear();
        });

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA REGISTRAR UN ODONTOLOGO                 ////
        ////////////////////////////////////////////////////////////////////////

        btnRegistrar.setOnMouseClicked(mouseEvent -> {
            beanOdontologo newOdon = new beanOdontologo(
                    txtCedula.getText(),
                    txtNombresOdontologo.getText(),
                    txtApellidosOdontologo.getText(),
                    txtTelefonoOdontologo.getText(),
                    txtCelularOdontologo.getText(),
                    txtEspecialidadOdontologo.getText(),
                    cbSede.getValue().toString());
            try {
                //ODONTOLOGO DATOS
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "begin distributed tran INSERT INTO Odontologo_Datos VALUES (?,?,?,?,?);" +
                        " commit");
                ps.setString(1, newOdon.getCedula_odon());
                ps.setString(2, newOdon.getTelefonoFijo_odon());
                ps.setString(3, newOdon.getCelular_odon());
                ps.setString(4, newOdon.getEspecialidad_odon());
                ps.setString(5, newOdon.getId_suc());
                ps.executeUpdate();

                //ODONTOLOGO_CITA

                ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "begin distributed tran INSERT INTO Odontologo_Cita VALUES (?,?,?,?);" +
                        " commit");
                ps.setString(1, newOdon.getCedula_odon());
                ps.setString(2, newOdon.getNombres_odon());
                ps.setString(3, newOdon.getApellidos_odon());
                ps.setString(4, newOdon.getId_suc());
                ps.executeUpdate();

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se registraron correctamente");
                a.setContentText("Se registró al odontologo: " + newOdon.getNombres_odon().toUpperCase());
                a.showAndWait();
                limpiarGuiRegistrar();
                //Ocultamos el panel
                paneRegistrarOdontologo.setVisible(false);
                paneOpciones.setVisible(true);
                regresarOpciones.setVisible(false);
                regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        tablaEditar.setOnMouseClicked(mouseEvent -> {
            index = tablaEditar.getSelectionModel().getFocusedIndex();
        });

        tablaEliminar.setOnMouseClicked(mouseEvent -> {
            index = tablaEliminar.getSelectionModel().getFocusedIndex();
        });


        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA CONSULTAR UN ODONTOLOGO                 ////
        ////////////////////////////////////////////////////////////////////////
        btnBuscarOdonto.setOnMouseClicked(mouseEvent -> {
            llenarColumnas();
            consultarInfoOdontologo(txtCiOdontologo.getText());
            tablaDatos.setVisible(true);
            tablaDatos.setItems(data);
        });


        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA EDITAR UN ODONTOLOGO                    ////
        ////////////////////////////////////////////////////////////////////////

        btnBuscarActualizar.setOnMouseClicked(mouseEvent -> {
            llenarColumnasEditar();
            consultarInfoOdontologo(txtCiActualizar.getText());
            tablaEditar.setVisible(true);
            tablaEditar.setItems(data);
            tablaEditar.setEditable(true);
        });

        btnActualizarActualizar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "UPDATE Odontologo_Datos SET " +
                        "telefonoFijo_odon=?, " +
                        "celular_odon=?, " +
                        "especialidad_odon=?, " +
                        "id_suc=? " +
                        " WHERE cedula_odon =?;" +
                        " commit");
                ps.setString(1, data.get(index).getTelefonoFijo_odon());
                ps.setString(2, data.get(index).getCelular_odon());
                ps.setString(3, data.get(index).getEspecialidad_odon());
                ps.setString(4, data.get(index).getId_suc());
                ps.setString(5, data.get(index).getCedula_odon());
                ps.executeUpdate();

                PreparedStatement ps1 = con.prepareStatement("SET XACT_ABORT ON; " +
                        "UPDATE Odontologo_Cita SET " +
                        "nombres_odon=?, " +
                        "apellidos_odon=?, " +
                        "id_suc=? " +
                        " WHERE cedula_odon =?;" +
                        " commit");
                ps1.setString(1, data.get(index).getNombres_odon());
                ps1.setString(2, data.get(index).getApellidos_odon());
                ps1.setString(3, data.get(index).getId_suc());
                ps1.setString(4, data.get(index).getCedula_odon());
                ps1.executeUpdate();

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se actualizaron correctamente");
                a.showAndWait();
                tablaEditar.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA ELIMINAR UN ODONTOLOGO                  ////
        ////////////////////////////////////////////////////////////////////////

        btnBuscarEliminar.setOnMouseClicked(mouseEvent -> {
            llenarColumnasEliminar();
            consultarInfoOdontologo(txtCiEliminar.getText());
            tablaEliminar.setVisible(true);
            tablaEliminar.setItems(data);
        });

        btnEliminarEliminar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("SET XACT_ABORT ON; " +
                        "delete from Odontologo_Cita where cedula_odon=?;\n" +
                        "delete from Odontologo_Datos where cedula_odon=?;" +
                        " commit");
                ps.setString(1, data.get(index).getCedula_odon());
                ps.setString(2, data.get(index).getCedula_odon());
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("El Odontologo: "+data.get(index).getNombres_odon()+", se elimino correctamente");
                data.remove(index);
                tablaEliminar.refresh();
                a.showAndWait();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void limpiarGuiRegistrar() {
        cbSede.getSelectionModel().clearSelection();
        txtCedula.setText("");
        txtNombresOdontologo.setText("");
        txtApellidosOdontologo.setText("");
        txtTelefonoOdontologo.setText("");
        txtCelularOdontologo.setText("");
        txtEspecialidadOdontologo.setText("");
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

    private void consultarInfoOdontologo(String numCiOdontologo){
        try{
            PreparedStatement ps;
            ResultSet rs;
            Connection con = Conexion.getConexion();
            if(numCiOdontologo.equals("")){
                ps = con.prepareStatement("select * from Odontologo_Info");
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanOdontologo (
                            rs.getString("cedula_odon"),
                            rs.getString("nombres_odon"),
                            rs.getString("apellidos_odon"),
                            rs.getString("telefonoFijo_odon"),
                            rs.getString("celular_odon"),
                            rs.getString("especialidad_odon"),
                            rs.getString("id_suc")
                    ));
                }
            }else{
                String where = " WHERE cedula_odon=?";

                ps = con.prepareStatement("select * from Odontologo_Info"+where);
                ps.setString(1, numCiOdontologo);
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanOdontologo (
                            rs.getString("cedula_odon"),
                            rs.getString("nombres_odon"),
                            rs.getString("apellidos_odon"),
                            rs.getString("telefonoFijo_odon"),
                            rs.getString("celular_odon"),
                            rs.getString("especialidad_odon"),
                            rs.getString("id_suc")
                    ));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void llenarColumnas(){
        //BORRAMOS LA INFORMACION DE LAS TABLAS
        tablaDatos.getItems().clear();
        tablaDatos.getColumns().clear();
        data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanOdontologo, String> colCiPaciente = new TableColumn<>("Cédula");
        colCiPaciente.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("cedula_odon"));
        colCiPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colCiPaciente.setOnEditCommit(data ->{
            data.getRowValue().setCedula_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colNombreOdonto = new TableColumn<>("Nombres");
        colNombreOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("nombres_odon"));
        colNombreOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreOdonto.setOnEditCommit(data ->{
            data.getRowValue().setNombres_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colApellidoOdonto = new TableColumn<>("Apellidos");
        colApellidoOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("apellidos_odon"));
        colApellidoOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidoOdonto.setOnEditCommit(data ->{
            data.getRowValue().setApellidos_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colTlfFijo = new TableColumn<>("Tlf. Fijo");
        colTlfFijo.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("telefonoFijo_odon"));
        colTlfFijo.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfFijo.setOnEditCommit(data ->{
            data.getRowValue().setTelefonoFijo_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colCellOdonto = new TableColumn<>("Tlf. Celular");
        colCellOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("celular_odon"));
        colCellOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colCellOdonto.setOnEditCommit(data ->{
            data.getRowValue().setCelular_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colEspecialidad = new TableColumn<>("Especialidad");
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("especialidad_odon"));
        colEspecialidad.setCellFactory(TextFieldTableCell.forTableColumn());
        colEspecialidad.setOnEditCommit(data ->{
            data.getRowValue().setEspecialidad_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colIdSuc = new TableColumn<>("ID Suc.");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        tablaDatos.getColumns().addAll(colCiPaciente,
                colNombreOdonto,
                colApellidoOdonto,
                colTlfFijo,
                colCellOdonto,
                colEspecialidad,
                colIdSuc);
    }

    public void llenarColumnasEditar(){
        //BORRAMOS LA INFORMACION DE LAS TABLAS
        tablaEditar.getItems().clear();
        tablaEditar.getColumns().clear();
        data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanOdontologo, String> colCiPaciente = new TableColumn<>("Cédula");
        colCiPaciente.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("cedula_odon"));
        colCiPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colCiPaciente.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setCedula_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colNombreOdonto = new TableColumn<>("Nombres");
        colNombreOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("nombres_odon"));
        colNombreOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreOdonto.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setNombres_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colApellidoOdonto = new TableColumn<>("Apellidos");
        colApellidoOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("apellidos_odon"));
        colApellidoOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidoOdonto.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setApellidos_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colTlfFijo = new TableColumn<>("Tlf. Fijo");
        colTlfFijo.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("telefonoFijo_odon"));
        colTlfFijo.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfFijo.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setTelefonoFijo_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colCellOdonto = new TableColumn<>("Tlf. Celular");
        colCellOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("celular_odon"));
        colCellOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colCellOdonto.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setCelular_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colEspecialidad = new TableColumn<>("Especialidad");
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("especialidad_odon"));
        colEspecialidad.setCellFactory(TextFieldTableCell.forTableColumn());
        colEspecialidad.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setEspecialidad_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colIdSuc = new TableColumn<>("ID Suc.");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            this.data.get(index).setId_suc(data.getNewValue());
        });

        tablaEditar.getColumns().addAll(colCiPaciente,
                colNombreOdonto,
                colApellidoOdonto,
                colTlfFijo,
                colCellOdonto,
                colEspecialidad,
                colIdSuc);
    }

    public void llenarColumnasEliminar(){
        //BORRAMOS LA INFORMACION DE LAS TABLAS
        tablaEliminar.getItems().clear();
        tablaEliminar.getColumns().clear();
        data.removeAll();
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanOdontologo, String> colCiPaciente = new TableColumn<>("Cédula");
        colCiPaciente.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("cedula_odon"));
        colCiPaciente.setCellFactory(TextFieldTableCell.forTableColumn());
        colCiPaciente.setOnEditCommit(data ->{
            data.getRowValue().setCedula_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colNombreOdonto = new TableColumn<>("Nombres");
        colNombreOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("nombres_odon"));
        colNombreOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreOdonto.setOnEditCommit(data ->{
            data.getRowValue().setNombres_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colApellidoOdonto = new TableColumn<>("Apellidos");
        colApellidoOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("apellidos_odon"));
        colApellidoOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidoOdonto.setOnEditCommit(data ->{
            data.getRowValue().setApellidos_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colTlfFijo = new TableColumn<>("Tlf. Fijo");
        colTlfFijo.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("telefonoFijo_odon"));
        colTlfFijo.setCellFactory(TextFieldTableCell.forTableColumn());
        colTlfFijo.setOnEditCommit(data ->{
            data.getRowValue().setTelefonoFijo_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colCellOdonto = new TableColumn<>("Tlf. Celular");
        colCellOdonto.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("celular_odon"));
        colCellOdonto.setCellFactory(TextFieldTableCell.forTableColumn());
        colCellOdonto.setOnEditCommit(data ->{
            data.getRowValue().setCelular_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colEspecialidad = new TableColumn<>("Especialidad");
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("especialidad_odon"));
        colEspecialidad.setCellFactory(TextFieldTableCell.forTableColumn());
        colEspecialidad.setOnEditCommit(data ->{
            data.getRowValue().setEspecialidad_odon(data.getNewValue());
        });

        TableColumn<beanOdontologo, String> colIdSuc = new TableColumn<>("ID Suc.");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanOdontologo, String>("id_suc"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_suc(data.getNewValue());
        });

        tablaEliminar.getColumns().addAll(colCiPaciente,
                colNombreOdonto,
                colApellidoOdonto,
                colTlfFijo,
                colCellOdonto,
                colEspecialidad,
                colIdSuc);
    }
}