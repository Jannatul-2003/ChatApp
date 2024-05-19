package org.example.chatapp;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class FileShareRequestController implements Initializable {
    private Button browseButton;
    public TextField filePathText;
    private InputStream inputStream;
    private Socket socket;
    private String fileName;
    public Label fileNameLabel;

    private long fileSize;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any necessary initialization code

    }

    public void setSocketController(Socket socket) {
        this.socket = socket;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        fileNameLabel.setText(fileName);
    }

    public void setFileSize(long size) {
        fileSize = size;
    }
    public void browseButtonClick(ActionEvent actionEvent) {
        browseButton = (Button)actionEvent.getSource();
        Stage stage = (Stage) browseButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        System.out.println("Selected Directory: " + selectedDirectory.getAbsolutePath());
        filePathText.setText(selectedDirectory.getAbsolutePath());
    }

    public  void saveButtonClick(ActionEvent actionEvent) throws IOException {
        FileOutputStream out = new FileOutputStream(filePathText.getText() + "/" +fileName);
        byte[] buffer = new byte[604*1024];
        int bytesRead;
        inputStream = socket.getInputStream();
//        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
//        printWriter.println("ACPT");
        long totalBytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
            double progress = (double) totalBytesRead / fileSize;

        }
        System.out.println("File received successfully.");
        out.close();
        inputStream.close();
        Button closeButton = (Button)actionEvent.getSource();
        closeButton.getScene().getWindow().hide();

    }

    public void rejectButtonClick(ActionEvent actionEvent) throws IOException {
//        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
//        printWriter.println("RJCT");
        socket.close();
        Button closeButton = (Button)actionEvent.getSource();
        closeButton.getScene().getWindow().hide();
    }

    private void updateProgress(long totalBytesRead, long fileSize) {
        // Calculate progress as a double between 0 and 1
        double progress = (double) totalBytesRead / fileSize;
        // Update the progress bar on the JavaFX Application Thread
    }

}
