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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientListController implements Initializable {
    @FXML
    public Button SearchButton;

    //
@FXML
private ListView<String> listView;

    @FXML
    private TextField SearchName;

@FXML
    private ObservableList<String> clients= FXCollections.observableArrayList();
@FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ConnectedUser> users = Peer.getInstance().getOnlineUsers();
        //clients= FXCollections.observableArrayList("Client 1", "Client 2", "Client 3", "Client 4", "Client 5");
        for(ConnectedUser user: users)
        {
            clients.add(user.getName());
        }
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
    @FXML
    public void refreshUserList() {
        //data.removeAll();
        listView.getItems().clear();
        ArrayList<ConnectedUser> users = Peer.getInstance().getOnlineUsers();
        //clients= FXCollections.observableArrayList("Client 1", "Client 2", "Client 3", "Client 4", "Client 5");
        for(ConnectedUser user: users)
        {
            clients.add(user.getName());
        }
        listView.setItems(clients);
        listView.getSelectionModel().selectedItemProperty().addListener(this::selectClient);
    }
}


