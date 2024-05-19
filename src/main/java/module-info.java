module org.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.chatapp to javafx.fxml;
    exports org.example.chatapp;
    exports org.example.chatapp.controller;
    opens org.example.chatapp.controller to javafx.fxml;
    exports org.example.chatapp.model;
    opens org.example.chatapp.model to javafx.fxml;
    exports org.example.chatapp.util;
    opens org.example.chatapp.util to javafx.fxml;

}