package org.example.chatapp;

import java.io.*;
import java.net.Socket;

public class ReceiveFiles implements Runnable {
    private Socket socket;

    public ReceiveFiles(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             FileOutputStream out = new FileOutputStream("received_file.bin")) {
            byte[] buffer = new byte[10024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("File received successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
