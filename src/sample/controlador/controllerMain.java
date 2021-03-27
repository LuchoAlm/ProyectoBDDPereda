package sample.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.binary.Base64;
import org.kordamp.ikonli.javafx.FontIcon;
import sample.modelo.Conexion;
import sample.modelo.beanLogin;
import sample.modelo.beanSucursal;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;


public class controllerMain implements Initializable {
    double x=0, y=0;
    private String key= "BdDistribuidas";
    private String pass = "";

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
                txtUsuario.setStyle("-jfx-focus-color:#00C5F0");
                icoUser.setOpacity(0.3);
            }
        });

        txtPassword.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // Gana el Foco
                icoPassword.setOpacity(1.0);
            }
            else {
                txtPassword.setStyle("-jfx-focus-color:#00c5f0");
                icoPassword.setOpacity(0.3);
            }
        });

        btnIngresar.setOnMouseClicked(mouseEvent -> {
            encode(txtPassword.getText());
            if(validarLog(txtUsuario.getText(), this.pass)){
                Stage s;
                s = (Stage) ((JFXButton) mouseEvent.getSource()).getScene().getWindow();
                s.close();
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/menuPrincipal.fxml"));

                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    root.setOnMousePressed(event ->{
                        x=event.getSceneX();
                        y= event.getSceneY();
                    });
                    root.setOnMouseDragged(event ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Error al cargar la vista");
                }
            }


        });
    }
    private void encode(String password){
        String encriptaciopn="";
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(this.key.getBytes("utf-8"));
            byte[] bytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key1 = new SecretKeySpec(bytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key1);
            byte[] plainTextBytes = password.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            encriptaciopn = new String(base64Bytes);
            this.pass = encriptaciopn;
        }catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    private boolean validarLog(String usuario, String contraseña){
        String where = " WHERE usuario ='"+usuario+"'";
        try{
            PreparedStatement ps;
            ResultSet rs;

            Connection con = Conexion.getConexion();
            ps = con.prepareStatement("SELECT * FROM PDLOGIN"+where);
            rs = ps.executeQuery();
            if(!rs.next()){
                mensajeErrorLogin("not-exist");
                return false;
            }else{
                beanLogin log = new beanLogin();
                //Cargamos el login
                System.out.println();
                do{
                    if(txtUsuario.getText().equals(rs.getString("usuario"))){
                        if(pass.equals(rs.getString("password"))){
                            return true;

                        }else{
                            mensajeErrorLogin("bad-user-pass");
                            txtUsuario.setStyle("-jfx-unfocus-color:#F65A5B");
                            txtPassword.setStyle("-jfx-unfocus-color:#F65A5B");
                            txtUsuario.setStyle("-jfx-focus-color:#F65A5B");
                            txtUsuario.requestFocus();
                            return false;
                        }
                    }else{
                        mensajeErrorLogin("bad-user-pass");
                        txtUsuario.setStyle("-jfx-unfocus-color:#F65A5B");
                        txtPassword.setStyle("-jfx-unfocus-color:#F65A5B");
                        txtUsuario.setStyle("-jfx-focus-color:#F65A5B");
                        txtUsuario.requestFocus();
                        return false;
                    }
                }while(rs.next());
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    private void mensajeErrorLogin(String condition){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error al iniciar sesión");
        if(condition.equals("not-exist")){
            alert.setHeaderText("El usuario no existe");
            alert.setContentText("Ooops, el Usuario no se encuentra registrado\n" +
                    "Comuníquese con el administrador para solucionar el problema");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        }else if(condition.equals("bad-user-pass")){
            alert.setHeaderText("Usuario o Password Incorrecto");
            alert.setContentText("El nombre de usuario o la contraseña no son correctos");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
        }



    }
}
