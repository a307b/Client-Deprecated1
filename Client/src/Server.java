import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Server {
    public static void main(String[] args) throws Exception {
        Path storedPrivateKeyDES = Paths.get("C:\\Client\\Client\\privateKeys\\pr01.dat");
        byte[] privateKeyBytes =  Files.readAllBytes(storedPrivateKeyDES);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        // Backend Stuff

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server ready");
            while(true) {
                Socket socket = serverSocket.accept();
                listen(privateKey, socket);
            }
        } catch (IOException e) {
            System.out.println("Could not bind to port: " + e.getMessage());
        }
    }

    protected static void listen(PrivateKey privateKey, Socket socket) throws Exception {
        try (DataInputStream dIn = new DataInputStream(socket.getInputStream())) {
            String clientAddress = socket.getInetAddress().getHostAddress();
            System.out.println("\r\nNew connection from " + clientAddress);


            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            int length = dIn.readInt();                    // read length of incoming message
            if (length > 0) {
                byte[] data = new byte[length];
                dIn.readFully(data, 0, data.length);   // reads the data
                // Decrypts the bytes
                System.out.println("Client connected, decrypts message.");
                byte[] decryptedData = cipher.doFinal(data);
                JSONParser parser = new JSONParser();
                JSONObject returnedJsonObj = (JSONObject) parser.parse(new String(decryptedData));
                System.out.println("Decrypted message :" + returnedJsonObj);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}