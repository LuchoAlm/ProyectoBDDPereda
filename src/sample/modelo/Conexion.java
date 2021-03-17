package sample.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConexion(){
        String conexionUrl= "jdbc:sqlserver://localhost:1433;" +
                "database=PeredaDentalSUC1;" +
                "user=sa;" +
                "password=P@ssw0rd;" +
                "loginTimeout=30;";
        try {
            Connection con = DriverManager.getConnection(conexionUrl);
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
