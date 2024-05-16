package org.example.chatapp;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.io.IOException;
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
private ArrayList<ConnectedUser> users;
@FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    refreshUserList();
listView.getSelectionModel().selectedItemProperty().addListener(this::selectClient);

    }
    @FXML

    public void selectClient (ObservableValue<? extends String> observable, String oldValue, String newValue) {
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        String getClient = (selectedItems.isEmpty())? "No Selected User" : selectedItems.toString();
        System.out.println(getClient);
        if(!getClient.equals("No Selected User"))
        {
            for(ConnectedUser user: users)
            {
                if(user.getName().equals(getClient))
                {
                    System.out.println("Selected User: "+user.getName() + " IP: "+user.getIp());
                    try {
                        Socket socket = user.getConnectedSocket();
                        //
                        LoginApplication.stage.close();
                        FXMLLoader fxmlLoader = new FXMLLoader(ChatController.class.getResource("Chat.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        ChatController chatController = fxmlLoader.getController();
                        chatController.setSocket_Label(socket,getClient);
                        LoginApplication.stage = new Stage();
                        LoginApplication.stage.setTitle("Chat Window");
                        LoginApplication.stage.setScene(scene);
                        LoginApplication.stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error initializing chat window or starting threads.");
                    }


                }
            }
        }
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
        listView.getItems().clear();
        users = Peer.getInstance().getOnlineUsers();
        //clients= FXCollections.observableArrayList("Client 1", "Client 2", "Client 3", "Client 4", "Client 5");
        for(ConnectedUser user: users)
        {
            clients.add(user.getName());
        }
        listView.setItems(clients);
        //listView.getSelectionModel().selectedItemProperty().addListener(this::selectClient);
    }
}


