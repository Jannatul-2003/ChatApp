package org.example.chatapp;

import java.io.*;
import java.net.*;

public class ReceiveMSG implements Runnable {
    private Socket socket;

    public ReceiveMSG(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
