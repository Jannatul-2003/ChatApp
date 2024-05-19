package org.example.chatapp.model;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.example.chatapp.LoginApplication;

import java.io.*;
import java.net.*;

public class ReceiveMSG implements Runnable {
    private Socket socket;
    private String userName;

    public ReceiveMSG(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                String finalMessage = message;
                Platform.runLater(() -> {
                    Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(LoginApplication.stage);  // Ensure LoginApplication.stage is accessible or pass primaryStage directly
                    dialog.setTitle("New Message from: "+this.userName);
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.setPadding(new Insets(20)); // Add padding around the VBox
                    Text messageText = new Text(finalMessage);
                    messageText.setWrappingWidth(260); // Ensure text wraps within the VBox
                    dialogVbox.getChildren().add(messageText);

                    Scene dialogScene = new Scene(dialogVbox, 400, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                });
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
