package org.example.chatapp;
import java.io.*;
import java.net.*;
import java.util.*;


public class Peer {
    private int portNumber;
    private String username;
    public static Map<String, String> clientMap; // Map to store username and IP address
    private List<Socket> connectedPeers;

    public Peer(int portNumber,String username){
        this.portNumber = portNumber;
        clientMap = new HashMap<>();
        this.connectedPeers = new ArrayList<>();
        this.username = username;
    }

    public void start() {
        try {
            // Get user name from user input
            //BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            //System.out.print("Enter your username: ");
           // String username = userInput.readLine();

            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is listening...");

            Thread serverThread = new Thread(() -> {
                try {
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());//to get the ip address of the client

                        // Start thread to handle receiving messages from this peer
                        Thread receiveThread = new Thread(new ReceiveMSG(clientSocket));
                        receiveThread.start();

                        // Add the client to the clientMap
                        synchronized (clientMap) {
                            clientMap.put(username, clientSocket.getInetAddress().getHostAddress());
                        }

                        connectedPeers.add(clientSocket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverThread.start();

            for(int i =0; i <= 255; i++){
            String ipAddress = GetLocalIP.getLocalIPAddress().substring(0, GetLocalIP.getLocalIPAddress().lastIndexOf(".")) + "." + i;
               if (!ipAddress.equals(GetLocalIP.getLocalIPAddress())){
                    Socket socket = new Socket();
                    try {
                        socket.connect(new InetSocketAddress(ipAddress, portNumber), 100); // Timeout after 1 second
                        System.out.println("Connected to peer: " + ipAddress);

                        // Start thread to handle sending messages to this peer
                        Thread sendThread = new Thread(new SendMSG(socket));
                        sendThread.start();

                        // Send username to peer
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(username);

                        connectedPeers.add(socket);
                    } catch (IOException e) {
                        // Connection failed, continue to the next IP address
                        System.out.println("Connection failed to: " + ipAddress);
                    }
                }
           }

            // Allow the user to search and message a specific client
            /*while (true) {
                System.out.print("Enter the username of the client you want to message (or type 'exit' to quit): ");
                String recipient = userInput.readLine();
                if (recipient.equals("exit")) {
                    break;
                }
                String recipientIPAddress = clientMap.get(recipient);
                if (recipientIPAddress == null) {
                    System.out.println("User not found.");
                    continue;
                }
                for (Socket peerSocket : connectedPeers) {
                    if (peerSocket.getInetAddress().getHostAddress().equals(recipientIPAddress)) {
                        // Start thread to handle sending messages to the specific peer
                        Thread sendToSpecificThread = new Thread(new SendMSG(peerSocket));
                        sendToSpecificThread.start();
                        break;
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}