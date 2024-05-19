package org.example.chatapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @FXML
    private ImageView attachedButton;

    @FXML
    private Text txtLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any necessary initialization code
    }

    public void setClientName(String name) {
        txtLabel.setText(name);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            if (this.socket != null) {
                this.out = new PrintWriter(socket.getOutputStream(), true);
            } else {
                System.err.println("Socket is null. Unable to get output stream.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String msgToSend = txtMsg.getText();
        if (!msgToSend.isEmpty()) {
            if (!msgToSend.matches(".*\\.(png|jpe?g|gif)$")) {
                displayMessage("You", msgToSend);

                try {
                    String ip = CurrentUser.getInstance().getConnectedOpenuser().getIp();
                    int port = CurrentUser.getInstance().getConnectedOpenuser().getPort();
                    out.println(msgToSend);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                txtMsg.clear();
            }
        }
    }

    private void displayMessage(String sender, String message) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 0, 10));

        Text text = new Text(message);
        text.setStyle("-fx-font-size: 14");
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.WHITE);

        hBox.getChildren().add(textFlow);

        HBox hBoxTime = new HBox();
        hBoxTime.setAlignment(Pos.CENTER_RIGHT);
        hBoxTime.setPadding(new Insets(0, 5, 5, 10));
        String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        Text time = new Text(stringTime);
        time.setStyle("-fx-font-size: 8");

        hBoxTime.getChildren().add(time);

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(hBoxTime);
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
    private void attachedButtonOnAction() throws IOException {
        System.out.println("attachedButtonOnAction");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Attach");
        Stage stage = (Stage) attachedButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        String ip = CurrentUser.getInstance().getConnectedOpenuser().getIp();
        int port = CurrentUser.getInstance().getConnectedOpenuser().getPort();
        System.out.println(ip + ":" + port +" offering Handshake");
        if (file != null) {
            String filePath = file.getAbsolutePath();
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 100);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("$FILE$" +CurrentUser.getInstance().getName());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String peerName = in.readLine();
            System.out.println(peerName + " Ready for file transfer");
            try {
                // Send File Name
                out.println(file.getName());
                out.println(file.length());
//                String offer = in.readLine();
//                if(!offer.equals("ACPT")) {
//                    vBox.getChildren().add(new Text("Me: Send Request Rejected by " + peerName));
//                    socket.close();
//                    return;
//                }
                // Get output stream from the socket
                OutputStream outputStream = socket.getOutputStream();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

                // Create file input stream to read file
                FileInputStream fileInputStream = new FileInputStream(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                // Read file and write to output stream
                byte[] buffer = new byte[604 * 1024];
                int bytesRead;

                while ((bytesRead = bufferedInputStream.read(buffer,0,603*1024)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }
                bufferedOutputStream.flush();
                vBox.getChildren().add(new Text("Me: File sent - " + file.getName()));

                // Flush and close streams
                bufferedOutputStream.flush();
                bufferedInputStream.close();
                socket.shutdownOutput();
                out.close();

                // Notify the user that the file has been sent

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
