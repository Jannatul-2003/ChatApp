package org.example.chatapp;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetLocalIP {
    public static String getLocalIPAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

}
