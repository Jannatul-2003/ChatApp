package org.example.chatapp;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientListController implements Initializable {
    @FXML
    public Button SerachButton;
    //
@FXML
private ListView<String> listView;

    @FXML
    private TextField SearchName;


    private ObservableList<String> clients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //clients = FXCollections.observableArrayList(Peer.clientMap.keySet());
        clients= FXCollections.observableArrayList("Client 1", "Client 2", "Client 3", "Client 4", "Client 5");
        listView.setItems(clients);
        listView.getSelectionModel().selectedItemProperty().addListener(this::selectClient);
    }
    @FXML

    public void selectClient(ObservableValue<? extends String> observable, String oldValue, String newValue){
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        String getClient = (selectedItems.isEmpty())? "No Selected User" : selectedItems.toString();
        System.out.println(getClient);
    }
@FXML
    private void SearchButtonOnAction() {
        String searchText = SearchName.getText().toLowerCase();
        if (searchText.isEmpty()) {
            listView.setItems(clients);
        } else {
            ObservableList<String> filteredClients = clients.stream()
                    .filter(client -> client.toLowerCase().contains(searchText))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            listView.setItems(filteredClients);
        }
    }
}

