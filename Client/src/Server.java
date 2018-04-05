import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.Cipher;
import java.io.DataInputStream;
        import java.net.InetAddress;
import java.net.Socket;
public class Server {

    private java.net.ServerSocket server;

    public Server(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.server = new java.net.ServerSocket(0, 1, InetAddress.getByName(ipAddress));
        else
            this.server = new java.net.ServerSocket(0, 1, InetAddress.getLocalHost());
    }

    // reads information that has been stored on the server
    protected void listen() throws Exception {
        PrivateKeys privatekeys = new PrivateKeys();
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);

        DataInputStream dIn = new DataInputStream(client.getInputStream());
        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        // Initiate the Cipher, telling it that it is going to Decrypt, giving it the private key
        cipher.init(Cipher.ENCRYPT_MODE, privatekeys.privateKey1);

        int length = dIn.readInt();                    // read length of incoming message
        if(length>0) {
            byte[] data = new byte[length];
            dIn.readFully(data, 0, data.length); // reads the data
            // Decrypts the bytes
            byte[] decryptedData = cipher.doFinal(data);
            JSONParser parser = new JSONParser();
            JSONObject returnedJsonObj = (JSONObject) parser.parse(new String(decryptedData));
            System.out.println(returnedJsonObj);
        }
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }


    public static void main(String[] args) throws Exception {
        Server app = new Server("");
        System.out.println("\r\nRunning Server: " +
                "Host=" + app.getSocketAddress().getHostAddress() +
                " Port=" + app.getPort());

        app.listen();
    }
}