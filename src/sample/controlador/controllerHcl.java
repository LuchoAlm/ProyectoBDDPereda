package sample.controlador;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerHcl implements Initializable {
    double x=0, y=0;
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
    private AnchorPane paneOpciones, paneRegistrarHClinica, paneConsultarHClinica, paneActualizarHClinica, paneEliminarHClinica;
    @FXML
    private JFXButton btnRegistrarHClinica,btnConsultarHClinica, btnActualizarHClinica, btnEliminarHClinica;

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
            paneActualizarHClinica.setVisible(false);
            paneEliminarHClinica.setVisible(false);
            paneConsultarHClinica.setVisible(false);
            paneRegistrarHClinica.setVisible(false);
            paneOpciones.setVisible(true);
            regresarOpciones.setVisible(false);
            regresar.setVisible(true);
        });

        btnRegistrarHClinica.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneRegistrarHClinica.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnConsultarHClinica.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneConsultarHClinica.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnActualizarHClinica.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneActualizarHClinica.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        btnEliminarHClinica.setOnMouseClicked(mouseEvent -> {
            paneOpciones.setVisible(false);
            paneEliminarHClinica.setVisible(true);
            regresar.setVisible(false);
            regresarOpciones.setVisible(true);
        });

        ////////////////////////////////////////////////////////////////////////
        ////            LOGICA PARA OCULTAR PANELES                         ////
        ////////////////////////////////////////////////////////////////////////
    }
}