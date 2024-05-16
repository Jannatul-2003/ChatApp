package org.example.chatapp;

import java.io.*;
import java.util.*;
import java.net.*;

public class CurrentUser {
    private static CurrentUser single_instance = null;
    private String name;
    private String ip;
    private ServerSocket serverSocket;
    private Map<String, String> clientMap;
    private CurrentUser()
    {
        this.clientMap = new HashMap<>();
    }
    public static synchronized CurrentUser getInstance()
    {
        if (single_instance == null)
            single_instance = new CurrentUser();

        return single_instance;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server is listening...");

            Thread serverThread = new Thread(() -> {
                try {
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                        // Add the client to the clientMap
                        synchronized (clientMap) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            String clientUsername = in.readLine();
                            clientMap.put(clientUsername, clientSocket.getInetAddress().getHostAddress());
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println(name);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
