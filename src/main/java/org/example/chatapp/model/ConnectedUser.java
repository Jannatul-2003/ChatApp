package org.example.chatapp.model;
import java.net.Socket;


public class ConnectedUser {
    private int id;
    private String name;
    private String ip;
    private Socket connectedSocket;
    private int port;

    public ConnectedUser(int id, String name, String ip, Socket connectedSocket, int port) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.connectedSocket = connectedSocket;
        this.port = port;
    }

    public Socket getConnectedSocket() {
        return connectedSocket;
    }
    public void setConnectedSocket(Socket connectedSocket) {
        this.connectedSocket = connectedSocket;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}