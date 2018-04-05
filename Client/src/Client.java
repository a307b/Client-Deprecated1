import org.json.simple.JSONObject;

import javax.crypto.Cipher;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private transient Socket socket;
    private transient Scanner sc;

    public Client(InetAddress serverAddress, int serverPort) throws Exception
    {
        this.socket = new Socket(serverAddress, serverPort);
        this.sc = new Scanner(System.in);
    }

    public Socket getSocket() {
        return socket;
    }

    // Writes input to the server
    public void writeToServer() throws IOException, Exception
    {
        // In following example the public key is taken from the PrivateKeys class
        // but in the actual implementation it should be received from the database

        PrivateKeys privatekeys = new PrivateKeys();
        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        // Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
        cipher.init(Cipher.ENCRYPT_MODE, privatekeys.publicKey1);

        // For now this is a pesuodo object of an file that is sent to the server
        JSONObject patientData = new JSONObject();
        patientData.put("hospitalname", "Randers Sygehus");
        // Turns the object into bytes
        byte[] patientDataBytes = patientData.toString().getBytes("UTF8");
        // Ciphers the bytes
        byte[] dataEncrypted = cipher.doFinal(patientDataBytes);

        // Writes the ciphered bytes to the server
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        dOut.writeInt(patientDataBytes.length);
        dOut.write(patientDataBytes);
    }

}

