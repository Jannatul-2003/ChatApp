package org.example.chatapp;
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

    public ArrayList<ConnectedUser> getOnlineUsers()
    {
        ArrayList<ConnectedUser> users=new ArrayList<ConnectedUser>();
        for(int i=0; i<=255; i++)
        {
            //String ipAddress = "10.42.0."+i;
            String ipAddress = Objects.requireNonNull(GetLocalIP.getLocalIPAddress()).substring(0, GetLocalIP.getLocalIPAddress().lastIndexOf(".")+1)+i;
            if(ipAddress.equals(GetLocalIP.getLocalIPAddress()))
              continue;
            Socket socket= new Socket();
            try {
                socket.connect(new InetSocketAddress(ipAddress, 12345), 10);
                System.out.println("Connected to peer: " + ipAddress);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(CurrentUser.getInstance().getName());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                users.add(new ConnectedUser(socket, ipAddress,in.readLine(),i));
            } catch (IOException e) {
                System.out.println("Failed to connect to peer: " + ipAddress);

            }
        }
        return users;
    }

}