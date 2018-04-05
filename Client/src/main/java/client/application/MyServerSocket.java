package client.application;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
        import java.net.InetAddress;
        import java.net.ServerSocket;
        import java.net.Socket;
public class MyServerSocket {

    private ServerSocket server;

    public MyServerSocket(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty())
            this.server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
        else
            this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
    }

    // reads information that has been stored on the server
    private void listen() throws Exception {
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);

        DataInputStream dIn = new DataInputStream(client.getInputStream());

        int length = dIn.readInt();                    // read length of incoming message
        if(length>0) {
            byte[] message = new byte[length];
            dIn.readFully(message, 0, message.length); // read the message
            JSONParser parser = new JSONParser();
            JSONObject returnedJsonObj = (JSONObject) parser.parse(new String(message));
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
        MyServerSocket app = new MyServerSocket("");
        System.out.println("\r\nRunning Server: " +
                "Host=" + app.getSocketAddress().getHostAddress() +
                " Port=" + app.getPort());

        app.listen();
    }
}