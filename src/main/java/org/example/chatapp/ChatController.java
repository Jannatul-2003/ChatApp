package org.example.chatapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    public AnchorPane pane;
    private Socket socket;
    private PrintWriter out;

    @FXML
    private ScrollPane scrollPain;

    @FXML
    private VBox vBox;

    @FXML
    private TextField txtMsg;

    @FXML
    private Button sendButton;

    @FXML
    private Button emojiButton;
    private EmojiPicker emojiPicker;

    @FXML
    private Button attachedButton;

    @FXML
    private Text txtLabel;

    public void setSocket_Label(Socket socket, String label) {
        this.socket = socket;
        txtLabel.setText(label);
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            startReceiving();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendButton.setOnAction(event -> sendMessage());
        txtMsg.setOnAction(event -> sendMessage());
        emojiButton.setOnAction(event -> emojiButtonOnAction());
        initializeEmojiPicker();
    }

    private void sendMessage() {
        String message = txtMsg.getText();
        if (!message.isEmpty()) {
            out.println(message);
            vBox.getChildren().add(new Text("Me: " + message));
            txtMsg.clear();
        }
    }

    private void startReceiving() {
        Thread receiveThread = new Thread(new ReceiveMSG(socket) {
            @Override
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = in.readLine()) != null) {
                        String finalMessage = message;
                        javafx.application.Platform.runLater(() -> vBox.getChildren().add(new Text("Peer: " + finalMessage)));
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveThread.start();
    }

    @FXML
    private void sendButtonOnAction() {
        sendMessage();
    }

    @FXML
    private void txtMsgOnAction() {
        sendMessage();
    }
@FXML
    private void initializeEmojiPicker() {
        // Create the EmojiPicker
        emojiPicker = new EmojiPicker();

        // Set the properties of the EmojiPicker
        VBox vBox = new VBox(emojiPicker);
        vBox.setPrefSize(150, 300);
        vBox.setLayoutX(400);
        vBox.setLayoutY(175);
        vBox.setStyle("-fx-font-size: 30");

        // Add the EmojiPicker to the scene
        pane.getChildren().add(vBox);

        // Set the emoji picker as hidden initially
        emojiPicker.setVisible(false);

        // Event handler for emoji picker selection
        emojiPicker.getEmojiListView().setOnMouseClicked(event -> {
            String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                txtMsg.setText(txtMsg.getText() + selectedEmoji);
            }
            emojiPicker.setVisible(false);
        });
    }
    @FXML
    private void emojiButtonOnAction() {
        if (emojiPicker.isVisible()) {
            emojiPicker.setVisible(false);
        } else {
            emojiPicker.setVisible(true);
        }
    }

    @FXML
    private void attachedButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Attach");
        Stage stage = (Stage) attachedButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String filePath = file.getAbsolutePath();
            try {
                // Get output stream from the socket
                OutputStream outputStream = socket.getOutputStream();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

                // Create file input stream to read file
                FileInputStream fileInputStream = new FileInputStream(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                // Read file and write to output stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }

                // Flush and close streams
                bufferedOutputStream.flush();
                bufferedInputStream.close();

                // Notify the user that the file has been sent
                vBox.getChildren().add(new Text("Me: File sent - " + file.getName()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
