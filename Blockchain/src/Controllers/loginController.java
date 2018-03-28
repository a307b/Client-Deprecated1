package client.Controllers;

import client.Application.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController implements Initializable {

    @FXML
    private JFXTextField usernameField;

    /*Two Factor Authentication
    @FXML
    private JFXTextField TFA;
    */

    @FXML
    private JFXPasswordField passwordField;

    private Stage stage;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public loginController(){
        connection = ConnectionUtil.connectdb();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handlesignInButtonAction(ActionEvent event) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        String sql = "SELECT role FROM Users WHERE username = ? and password = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Login Failed");
            } else {
                System.out.println("Login Successful");
                if (resultSet.getInt(1) == 0) {

                } else {
                    System.out.println("Is patient");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
