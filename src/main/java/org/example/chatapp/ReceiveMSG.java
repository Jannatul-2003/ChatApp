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
                if (message.equals("FILE")) {
                    receiveFile();
                } else {
                    System.out.println(message);
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFile() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // Create file output stream to write received file
            FileOutputStream fileOutputStream = new FileOutputStream("received_file.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            // Read from input stream and write to file
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }

            // Flush and close streams
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();

            // Notify the user that the file has been received
            System.out.println("File received successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
