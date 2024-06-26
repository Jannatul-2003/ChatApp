package org.example.chatapp.controller;
import org.example.chatapp.model.CurrentUser;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.chatapp.LoginApplication;

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
        }
        else if(CurrentUser.getInstance().isInClientMap(usernameTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Username already exists");
            alert.showAndWait();
        }
        else {
            String username = usernameTextField.getText();
            System.out.println("Username in LoginFormController: " + username);
            CurrentUser.getInstance().setName(username);
            CurrentUser.getInstance().start();
           //
            LoginApplication.stage.close();
            LoginApplication.stage = new Stage();
            //
            FXMLLoader fxmlLoader = new FXMLLoader(LoginFormController.class.getResource("/org/example/chatapp/ClientList.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LoginApplication.stage.setTitle("Client List");
            LoginApplication.stage.setScene(scene);
            LoginApplication.stage.show();
        }
    }
}