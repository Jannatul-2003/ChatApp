package org.example.chatapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage PrimaryStage) throws IOException {
        stage=PrimaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));


        PrimaryStage.setScene(new Scene(loader.load()));
        PrimaryStage.setTitle("Sign Up Page");
        PrimaryStage.centerOnScreen();
        PrimaryStage.setResizable(false);
        PrimaryStage.show();
    }
    public static void changeScene(Scene newScene) {
        stage.setScene(newScene);
    }
    public static void main(String[] args) {
        launch(args);
    }
}