package org.example.chatapp.model;
import org.example.chatapp.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    private static CurrentUser single_instance = null;

    private String name;
    private String ip;
    private ServerSocket serverSocket;
    private Map<String, String> clientMap;
    private ConnectedUser connectedOpenuser;
    private CurrentUser()
    {
        this.clientMap = new HashMap<>();
    }
    private static final int[] PORTS = {25000};
    public static synchronized CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }
    public boolean isInClientMap(String username) {
        return clientMap.containsKey(username);
    }

    public void start() {
        boolean socketCreated = false;
        for (int port : PORTS) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server is listening on port " + port);
                socketCreated = true;
                startServerThread();
                break; // Break out of the loop if a port is successfully bound
            } catch (IOException e) {
                System.err.println("Failed to start server on port " + port);
            }
        }

        if (!socketCreated) {
            System.err.println("Failed to start server on any port.");
            return;
        }
    }

    private void startServerThread() {
        Thread serverThread = new Thread(() -> {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    String clientUsername;
                    synchronized (clientMap) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        clientUsername = in.readLine();
                        if(!clientUsername.startsWith("$FILE$")){
                            clientMap.put(clientUsername, clientSocket.getInetAddress().getHostAddress());
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println(name);
                        }
                    }
                    if(clientUsername.startsWith("$FILE$")){
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println(name);
                        Thread fileReceiveThread = new Thread(new ReceiveFiles(clientSocket));
                        fileReceiveThread.start();
                    }
                    else{
                        Thread msgReceiveThread = new Thread(new ReceiveMSG(clientSocket, clientUsername));
                        msgReceiveThread.start();
                    }



                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConnectedUser getConnectedOpenuser() {
        return connectedOpenuser;
    }

    public void setConnectedOpenuser(ConnectedUser connectedOpenuser) {
        this.connectedOpenuser = connectedOpenuser;
    }
}
