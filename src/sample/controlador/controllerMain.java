package sample.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class controllerMain implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnIngresar;


    @FXML
    private FontIcon icoUser, icoPassword, exit, minimice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        icoPassword.setOpacity(0.3);
        icoUser.setOpacity(0.3);

        exit.setOnMouseClicked(event ->{
            System.exit(0);
        });

        minimice.setOnMouseClicked(event ->{

            Stage s;
            s = (Stage) ((FontIcon) event.getSource()).getScene().getWindow();
            s.setIconified(true);

            //System.exit(0);
        });

        txtUsuario.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { //Gana el Foco
                icoUser.setOpacity(1.0);
            }
            else {
                icoUser.setOpacity(0.3);
            }
        });

        txtPassword.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // Gana el Foco
                icoPassword.setOpacity(1.0);
            }
            else {
                icoPassword.setOpacity(0.3);
            }
        });

        btnIngresar.setOnMouseClicked(mouseEvent -> {
            Stage s;
            s = (Stage) ((JFXButton) mouseEvent.getSource()).getScene().getWindow();
            s.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/menuPrincipal.fxml"));

                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("Error al cargar la vista");
            }


        });



    }
}
