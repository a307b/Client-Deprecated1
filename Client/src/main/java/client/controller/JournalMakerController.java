package client.controller;
import client.application.MyClientSocket;

import java.net.InetAddress;

public class JournalMakerController{
    public static void main(String[] args) throws Exception {

        MyClientSocket client = new MyClientSocket(
        InetAddress.getByName("172.24.61.48"),
        Integer.parseInt("59746"));

        System.out.println("\r\nConnected to Server: " + client.getSocket().getInetAddress());
        client.start();
    }
}