package org.example.chatapp;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientListController implements Initializable {

    @FXML
    private ListView<String> ListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] clients = {"Client 1", "Client 2", "Client 3"};//hashmap key value pair
        ListView.getItems().addAll(clients);
        ListView.getSelectionModel().selectedItemProperty().addListener(this::selectClient);
    }
    public void selectClient(ObservableValue<? extends String> observable, String oldValue, String newValue){
        ObservableList<String> selectedItems = ListView.getSelectionModel().getSelectedItems();
        String getClient = (selectedItems.isEmpty())? "No Selected User" : selectedItems.toString();
        System.out.println(getClient);
    }

}
