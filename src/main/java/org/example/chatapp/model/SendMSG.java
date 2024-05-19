package org.example.chatapp.model;
import org.example.chatapp.*;

import java.io.*;
import java.net.*;

public class SendMSG implements Runnable {
    private Socket socket;

    public SendMSG(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}