package org.example.chatapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    public TextField usernameTextField;
    public void logInButtonOnAction() throws IOException {
        if (usernameTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter your username");
            alert.showAndWait();
        } else {
            String username = usernameTextField.getText();
            System.out.println("Username in LoginFormController: " + username);
            Peer peer=new Peer(12346,username);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginFormController.class.getResource("ClientList.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LoginApplication.stage.setTitle("Client List");
            LoginApplication.stage.setScene(scene);
            LoginApplication.stage.show();
        }
    }
}