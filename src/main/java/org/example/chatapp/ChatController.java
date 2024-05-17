package org.example.chatapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private ImageView sendButton;

    @FXML
    private Button emojiButton;
    //private EmojiPicker emojiPicker;

    @FXML
    private ImageView attachedButton;

    @FXML
    private Text txtLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sendButton.setOnAction(event -> sendMessage());
        //txtMsg.setOnAction(event -> sendMessage());
    }

    public void setClientName(String name) {
        txtLabel.setText(name);
    }
    private void sendMessage() {
        String message = txtMsg.getText();
        if (!message.isEmpty()) {
            out.println(message);
            vBox.getChildren().add(new Text("Me: " + message));
            txtMsg.clear();
        }
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
    private void attachedButtonOnAction() {
        System.out.println("attachedButtonOnAction");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Attach");
        Stage stage = (Stage) attachedButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String filePath = file.getAbsolutePath();
            try {
                // Get output stream from the socket
                OutputStream outputStream = CurrentUser.getInstance().getConnectedOpenuser().getConnectedSocket().getOutputStream();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

                // Create file input stream to read file
                FileInputStream fileInputStream = new FileInputStream(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                // Read file and write to output stream
                byte[] buffer = new byte[10024];
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
