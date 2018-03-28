package client.Application;

import java.sql.*;

public class ConnectionUtil {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException ex) {
            System.err.println("Unable to load MySQL Driver");
        }
    }

    public static Connection connectdb() {
        try {
            String jdbcUrl = "jdbc:mysql://freshcase.dk/freshcas_p2";
            Connection conn = DriverManager.getConnection(jdbcUrl, "freshcas_p2", "kris!2345Q");
            return conn;
        } catch (Exception e) {
            System.out.println("Connection failed, check connection details, or the server might be offline");
            return null;
        }
    }
}