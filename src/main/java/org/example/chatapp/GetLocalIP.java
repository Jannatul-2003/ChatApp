package org.example.chatapp;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class GetLocalIP {
    public static ArrayList<String> getLocalIPAddress() {
        ArrayList < String > ips = new ArrayList <String > ();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        //System.out.println("Local IP Address: " + address.getHostAddress());
                        ips.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ips;
    }

}
