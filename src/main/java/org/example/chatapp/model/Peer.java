package org.example.chatapp.model;

import org.example.chatapp.util.GetLocalIP;

import java.io.*;
import java.net.*;
import java.util.*;


public class Peer {
    private static Peer single_instance = null;
    private String s;

    public Peer() {
        s = "Hello I am a string part of Peer class";
    }

    public static synchronized Peer getInstance() {
        if (single_instance == null)
            single_instance = new Peer();
        return single_instance;
    }

    public ArrayList<ConnectedUser> getOnlineUsers() {
        ArrayList<ConnectedUser> users = new ArrayList<ConnectedUser>();
        //for (int j = 25002; j >= 25000; j--){
            for (int i = 0; i <= 255; i++) {
                for (String ip : GetLocalIP.getLocalIPAddress()) {
                    String ipAddress = ip.substring(0, ip.lastIndexOf(".") + 1) + i;
                    //if (ipAddress.equals(ip))
                    {
                        Socket socket = new Socket();

                        try {
                            //System.out.println(j);
                            socket.connect(new InetSocketAddress(ipAddress, 25000), 10); // Timeout after 1 second
                            System.out.println("Connected to peer: " + ipAddress);

                            // Send username to peer
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println(CurrentUser.getInstance().getName());
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            users.add(new ConnectedUser(i, in.readLine(), ipAddress,socket,25000));
                            continue;
                        } catch (IOException e) {
                            // Connection failed, continue to the next IP address
                            System.out.println("Connection failed to: " + ipAddress);
                        }

                    }
                }
            }


        return users;
    }
}