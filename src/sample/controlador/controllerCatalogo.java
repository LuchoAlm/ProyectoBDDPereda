package sample.controlador;

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
import sample.modelo.beanSucursal;
import sample.modelo.beanTratamiento;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class controllerCatalogo implements Initializable {
    double x=0, y=0;
    private ObservableList<beanTratamiento> data = FXCollections.observableArrayList();
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
    private AnchorPane paneOpciones, paneRegistrarCatalogo, paneConsultarCatalogo, paneActualizarCatalogo, paneEliminarCatalogo;
    @FXML
    private JFXButton btnRegistrarCatalogo,btnConsultarCatalogo, btnActualizarCatalogo, btnEliminarCatologo;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Registro Tratamiento                             ///
    //////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdTratamiento, txtNombreTratamiento, txtDescripcionTratamiento, txtPrecioTratamiento;
    @FXML
    private JFXButton btnRegistrarTra, btnRegistrarCancelar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Consulta Tratamiento                             ///
    //////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdBuscar;
    @FXML
    private JFXButton btnBuscarConsultar, btnCancelarConsultar;
    @FXML
    private TableView<beanTratamiento> tablaDatos;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Actualizar Tratamiento                             ///
    //////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdCatalogoActualizar;
    private int index;

    private String id_trat;
    private String nombre_trat;
    private String descripcion_trat;
    private float precio_trat;
    @FXML
    private JFXButton btnBuscarActualizar, btnCancelarActualizar, btnActualizarActualizar;
    @FXML
    private TableView<beanTratamiento> tablaEditar;

    //////////////////////////////////////////////////////////////////////
    ////        Panel Eliminar Tratamiento                             ///
    //////////////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField txtIdTratamientoEliminar;
    @FXML
    private JFXButton btnBuscarEliminar, btnCancelarEliminar, btnEliminarEliminar;
    @FXML
    private TableView<beanTratamiento> tablaEliminar;



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
        ////       FIN LOGICA PARA REGRESAR A CUALQUIERA DE LAS VENTANAS    ////
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA OCULTAR PANELES                         ////
        ////////////////////////////////////////////////////////////////////////
        regresarOpciones.setOnMouseClicked(mouseEvent -> {
            paneActualizarCatalogo.setVisible(false);
            paneEliminarCatalogo.setVisible(false);
            paneConsultarCatalogo.setVisible(false);
            paneRegistrarCatalogo.setVisible(false);
            paneOpciones.setVisible(true);
            regresarOpciones.setVisible(false);
            regresar.setVisible(true);
        });

        btnRegistrarCatalogo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneRegistrarCatalogo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnConsultarCatalogo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneConsultarCatalogo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnActualizarCatalogo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneActualizarCatalogo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnEliminarCatologo.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarCatalogo.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PANEL REGISTRAR SUCURSAL                     ////
        ////////////////////////////////////////////////////////////////////////

        btnRegistrarTra.setOnMouseClicked(mouseEvent -> {
            beanTratamiento newTra = new beanTratamiento(
                    txtIdTratamiento.getText().toUpperCase(),
                    txtNombreTratamiento.getText(),
                    txtDescripcionTratamiento.getText(),
                    Float.parseFloat(txtPrecioTratamiento.getText()));

            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("INSERT INTO TRATAMIENTO VALUES (?,?,?,?, NEWID())");
                ps.setString(1, newTra.getId_trat());
                ps.setString(2, newTra.getNombre_trat());
                ps.setString(3, newTra.getDescripcion_trat());
                ps.setFloat(4, newTra.getPrecio_trat());
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se registraron correctamente");
                a.setContentText("Se agregó el tratamiento: " + txtNombreTratamiento.getText());
                a.showAndWait();
                limpiarPaneRegistrar();
                paneRegistrarCatalogo.setVisible(false);
                paneOpciones.setVisible(true);
                regresarOpciones.setVisible(false);
                regresar.setVisible(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        btnRegistrarCancelar.setOnMouseClicked(mouseEvent -> {
            limpiarPaneRegistrar();
            txtIdTratamiento.requestFocus();
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
                    ps = con.prepareStatement("SELECT * FROM TRATAMIENTO");
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanTratamiento (
                                rs.getString("id_trat"),
                                rs.getString("nombre_trat"),
                                rs.getString("descripcion_trat"),
                                rs.getFloat("precio_trat")
                        ));
                        tablaDatos.setItems(data);
                    }
                }else{
                    String where = " WHERE id_trat=?";
                    ps = con.prepareStatement("SELECT * FROM TRATAMIENTO"+where);
                    ps.setString(1, txtIdBuscar.getText());
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanTratamiento (
                                rs.getString("id_trat"),
                                rs.getString("nombre_trat"),
                                rs.getString("descripcion_trat"),
                                rs.getFloat("precio_trat")
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
                if(txtIdCatalogoActualizar.getText().trim().isEmpty()){
                    ps = con.prepareStatement("SELECT * FROM TRATAMIENTO");
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanTratamiento (
                                rs.getString("id_trat"),
                                rs.getString("nombre_trat"),
                                rs.getString("descripcion_trat"),
                                rs.getFloat("precio_trat")
                        ));
                        tablaEditar.setItems(data);
                    }
                }else{
                    String where = " WHERE id_trat=?";
                    ps = con.prepareStatement("SELECT * FROM TRATAMIENTO"+where);
                    ps.setString(1, txtIdCatalogoActualizar.getText());
                    rs = ps.executeQuery();
                    while (rs.next()){
                        data.add(new beanTratamiento (
                                rs.getString("id_trat"),
                                rs.getString("nombre_trat"),
                                rs.getString("descripcion_trat"),
                                rs.getFloat("precio_trat")
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
            id_trat = tablaEditar.getItems().get(index).getId_trat();
            nombre_trat =tablaEditar.getItems().get(index).getNombre_trat();
            descripcion_trat= tablaEditar.getItems().get(index).getDescripcion_trat();
            precio_trat = tablaEditar.getItems().get(index).getPrecio_trat();
        });

        btnActualizarActualizar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("UPDATE TRATAMIENTO SET id_trat=?, nombre_trat=?, " +
                        "descripcion_trat=?, precio_trat=? WHERE id_trat =?");
                ps.setString(1, id_trat);
                ps.setString(2, nombre_trat);
                ps.setString(3, descripcion_trat);
                ps.setFloat(4, precio_trat);
                ps.setString(5, id_trat);
                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Los datos se actualizaron correctamente");
                data.get(index).setId_trat(id_trat);
                data.get(index).setNombre_trat(nombre_trat);
                data.get(index).setDescripcion_trat(descripcion_trat);
                data.get(index).setPrecio_trat(precio_trat);
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
                ps = con.prepareStatement("SELECT * FROM TRATAMIENTO");
                rs = ps.executeQuery();
                while (rs.next()){
                    data.add(new beanTratamiento (
                            rs.getString("id_trat"),
                            rs.getString("nombre_trat"),
                            rs.getString("descripcion_trat"),
                            rs.getFloat("precio_trat")
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
            id_trat = tablaEliminar.getItems().get(index).getId_trat();
        });

        btnEliminarEliminar.setOnMouseClicked(mouseEvent -> {
            try {
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement("DELETE FROM TRATAMIENTO WHERE id_trat =?");
                ps.setString(1, id_trat);
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
    private void limpiarPaneRegistrar(){
        txtIdTratamiento.setText("");
        txtNombreTratamiento.setText("");
        txtDescripcionTratamiento.setText("");
        txtPrecioTratamiento.setText("");
    }

    public void llenarColumnas(){
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanTratamiento, String> colIdSuc = new TableColumn<>("ID TRAT");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("id_trat"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data ->{
            data.getRowValue().setId_trat(data.getNewValue());
        });

        TableColumn<beanTratamiento, String> colNombre = new TableColumn<>("Nombre Trat");
        colNombre.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("nombre_trat"));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombre.setOnEditCommit(data ->{
            data.getRowValue().setNombre_trat(data.getNewValue());
        });

        TableColumn<beanTratamiento, String> colDirSuc = new TableColumn<>("Descripción");
        colDirSuc.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("descripcion_trat"));
        colDirSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirSuc.setOnEditCommit(data ->{
            data.getRowValue().setDescripcion_trat(data.getNewValue());
        });

        TableColumn<beanTratamiento, Float> colPrecio = new TableColumn<>("$ Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<beanTratamiento, Float>("precio_trat"));
        colPrecio.setOnEditCommit(data ->{
            data.getRowValue().setPrecio_trat(Float.parseFloat(String.valueOf(data.getNewValue())));
        });

        tablaDatos.getColumns().addAll(colIdSuc, colNombre, colDirSuc, colPrecio);
    }

    public void llenarColumnasEditar(){
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanTratamiento, String> colIdSuc = new TableColumn<>("ID TRAT");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("id_trat"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colIdSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            id_trat=data.getNewValue();
        });

        TableColumn<beanTratamiento, String> colNombre = new TableColumn<>("Nombre Trat");
        colNombre.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("nombre_trat"));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombre.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            nombre_trat=data.getNewValue();
        });

        TableColumn<beanTratamiento, String> colDirSuc = new TableColumn<>("Descripción");
        colDirSuc.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("descripcion_trat"));
        colDirSuc.setCellFactory(TextFieldTableCell.forTableColumn());
        colDirSuc.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            descripcion_trat=data.getNewValue();
        });

        TableColumn<beanTratamiento, Float> colPrecio = new TableColumn<>("$ Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<beanTratamiento, Float>("precio_trat"));
        colPrecio.setOnEditCommit(data -> {
            btnActualizarActualizar.setVisible(true);
            precio_trat=data.getNewValue();
        });

        tablaEditar.getColumns().addAll(colIdSuc, colNombre, colDirSuc, colPrecio);
    }

    public void llenarColumnasBorrar(){
        //AÑADIMOS LA INFORMACION A LAS COLUMNAS
        TableColumn<beanTratamiento, String> colIdSuc = new TableColumn<>("ID TRAT");
        colIdSuc.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("id_trat"));
        colIdSuc.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<beanTratamiento, String> colNombre = new TableColumn<>("Nombre Trat");
        colNombre.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("nombre_trat"));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<beanTratamiento, String> colDirSuc = new TableColumn<>("Descripción");
        colDirSuc.setCellValueFactory(new PropertyValueFactory<beanTratamiento, String>("descripcion_trat"));
        colDirSuc.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<beanTratamiento, Float> colPrecio = new TableColumn<>("$ Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<beanTratamiento, Float>("precio_trat"));

        tablaEliminar.getColumns().addAll(colIdSuc, colNombre, colDirSuc, colPrecio);
    }
}