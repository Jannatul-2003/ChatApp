package org.example.chatapp;
import java.net.Socket;

public class ConnectedUser {
    private int id;
    private String name;
    private String ip;
    private Socket connectedSocket;

    public ConnectedUser(Socket connectedSocket, String ip, String name, int id) {
        this.connectedSocket = connectedSocket;
        this.ip = ip;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return name;

    }
}