package client.application;

import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket
{
    private transient Socket socket;
    private transient Scanner sc;

    public MyClientSocket(InetAddress serverAddress, int serverPort) throws Exception
    {
        this.socket = new Socket(serverAddress, serverPort);
        this.sc = new Scanner(System.in);
    }

    public Socket getSocket() {
        return socket;
    }

    // Writes input to the server
    public void start() throws IOException
    {
        JSONObject patientData = new JSONObject();
        patientData.put("hospitalname", "Randers Sygehus");
        byte[] patientDataBytes = patientData.toString().getBytes("UTF8");

        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

        dOut.writeInt(patientDataBytes.length); // write length of the message
        dOut.write(patientDataBytes);
    }

}

