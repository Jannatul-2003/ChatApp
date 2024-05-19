# CSEDU_OneShare Application

This is a JavaFX-based peer-to-peer (P2P) application that allows users to connect, send messages, and share files with each other.

## Features
- **Login Page**: Users enter a nickname to connect.
- **Connected Users List**: Displays a list of connected users.
- **Messaging**: Send messages to other users.
- **File Sharing**: Share files with other users.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- JavaFX SDK
- Git (for cloning the repository)

## Installation

### Step 1: Clone the Repository
Clone the repository to your local machine using the following command:
```bash
git clone https://github.com/your-username/p2p-messaging-app.git

cd p2p-messaging-app
export PATH_TO_FX=/path/to/javafx-sdk/lib
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -d out src/*.java