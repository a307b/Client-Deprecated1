import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("JournalMaker.fxml"));
        primaryStage.setTitle("UserSearch");
        primaryStage.setScene(new Scene(root, 840, 580));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception{
        Path storedPublicKeyDES = Paths.get("C:\\Client\\Client\\privateKeys\\pu01.dat");
        byte[] publicKeyBytes =  Files.readAllBytes(storedPublicKeyDES);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        //KeyGenerator keys = new KeyGenerator();
        //keys.savePrivateKey("pr01");
        runClient(publicKey);
        launch(args);
    }

    public static void runClient(PublicKey publicKey) throws Exception {
        // Connects to the server where files are and should be stored
        Client client = new Client(
                InetAddress.getByName("192.168.1.15"),
                Integer.parseInt("62757"), publicKey);

        System.out.println("\r\nConnected to Server: " + client.getSocket().getInetAddress());
        client.writeToServer();
    }

}
