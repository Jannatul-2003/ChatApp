package org.example.chatapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class EmojiPicker extends VBox {
    private final ListView<String> emojiListView;
    private EmojiSelectionListener emojiSelectionListener;

    public EmojiPicker() {
        // Define the list of emojis as Unicode characters
        String[] emojiList = new String[]{
                "😂", "❤️", "😍", "🤣", "😊", "🙏", "💖", "😢", "🔥", "😎",
                "👍", "🥳", "😏", "😂", "😉", "🤔", "😆", "😲", "😳", "😴",
                "😉", "💪", "😐", "😘", "😜", "😱", "🤓", "😡", "👋", "💙"
                // Add more emojis as needed...
        };

        ObservableList<String> emojis = FXCollections.observableArrayList(Arrays.asList(emojiList));

        // Create the emoji list view
        emojiListView = new ListView<>(emojis);

        // Customize the appearance of the list view
        emojiListView.setPrefHeight(200); // Adjust height as needed
        emojiListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-font-size: 20"); // Customize font size
            }
        });

        // Set up event handling for emoji selection
        emojiListView.setOnMouseClicked((MouseEvent event) -> {
            String selectedEmoji = emojiListView.getSelectionModel().getSelectedItem();
            if (selectedEmoji != null && emojiSelectionListener != null) {
                emojiSelectionListener.onEmojiSelected(selectedEmoji);
            }
        });

        // Create an HBox to hold the emoji list view
        HBox hBox = new HBox(emojiListView);
        hBox.setPadding(new Insets(10));

        // Add the HBox to the EmojiPicker
        getChildren().add(hBox);
    }

    // Set a listener for emoji selection
    public void setEmojiSelectionListener(EmojiSelectionListener listener) {
        this.emojiSelectionListener = listener;
    }

    public interface EmojiSelectionListener {
        void onEmojiSelected(String emoji);
    }

    // Expose the ListView
    public ListView<String> getEmojiListView() {
        return emojiListView;
    }
}
