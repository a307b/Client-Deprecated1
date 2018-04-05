import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetAddress;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("JournalMaker.fxml"));
        primaryStage.setTitle("UserSearch");
        primaryStage.setScene(new Scene(root, 840, 580));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception{
        // Connects to the server where files are and should be stored
        PrivateKeys privatekeys = new PrivateKeys();
        System.out.println(privatekeys.publicKey1);
        System.out.println(privatekeys.privateKey1);
        Client client = new Client(
                InetAddress.getByName("192.168.1.15"),
                Integer.parseInt("61651"), privatekeys);

        System.out.println("\r\nConnected to Server: " + client.getSocket().getInetAddress());
        client.writeToServer();
        launch(args);
    }

}
