package org.example.chatapp;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ReceiveFiles implements Runnable {
    private Socket socket;

    public ReceiveFiles(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Parent root;
        try  {
            BufferedReader bfrIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fileName = bfrIn.readLine();
            long fileSize = bfrIn.read();
            InputStream in = socket.getInputStream();

            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("FileShareRequest.fxml"));

                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 500, 250);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FileShareRequestController controller = fxmlLoader.getController();
                controller.setSocketController(socket);
                controller.setFileName(fileName);
                controller.setFileSize(fileSize);
                Stage stage = new Stage();
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
            });;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
