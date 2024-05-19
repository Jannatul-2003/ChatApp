package org.example.chatapp;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientListController implements Initializable {

    @FXML
    private ListView<ConnectedUser> userListView;

    @FXML
    private TextField SearchName;

    @FXML
    private Button SearchButton;

    private ObservableList<ConnectedUser> users;
    private ConnectedUser selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = FXCollections.observableArrayList(Peer.getInstance().getOnlineUsers());
        userListView.setItems(users);
        userListView.getSelectionModel().selectedItemProperty().addListener(this::selectClient);
    }

    private void selectClient(ObservableValue<? extends ConnectedUser> observableValue, ConnectedUser oldUser, ConnectedUser newUser) {
        selectedUser = newUser;
        if (newUser != null) {
            System.out.println(newUser.getIp() + newUser.getName());
        }
    }

    public void refreshUserList(ActionEvent actionEvent) {
        userListView.getItems().clear();
        users = FXCollections.observableArrayList(Peer.getInstance().getOnlineUsers());
        userListView.setItems(users);
    }

    public void sendButtonClick(ActionEvent actionEvent) throws IOException, IOException {
        if (selectedUser != null) {
            CurrentUser.getInstance().setConnectedOpenuser(selectedUser);
            //LoginApplication.stage.close();
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                newStage.setTitle("New Chat: " + selectedUser.getIp());
                FXMLLoader fxmlLoader = new FXMLLoader(ClientListController.class.getResource("Chat.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ChatController controller = fxmlLoader.getController();
                controller.setClientName(selectedUser.getName());
                controller.setSocket(selectedUser.getConnectedSocket());
                newStage.setScene(scene);
                newStage.show();
            });
        }
    }

    @FXML
    private void SearchButtonOnAction(ActionEvent actionEvent) {
        String searchText = SearchName.getText().toLowerCase();
        if (searchText.isEmpty()) {
            userListView.setItems(users);
        } else {
            ObservableList<ConnectedUser> filteredUsers = users.stream()
                    .filter(user -> user.getName().toLowerCase().contains(searchText))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            userListView.setItems(filteredUsers);
        }
    }
}
