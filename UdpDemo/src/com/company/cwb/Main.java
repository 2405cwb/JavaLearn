package com.company.cwb;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        try {
            int port = 8080;
            String address = "localhost";
            InetAddress localhost = InetAddress.getByName(address);
            DatagramSocket socket = new DatagramSocket();
            String msg = "output somethingsdsadsssssdasfsaxzfsfaswaaaaaaa55555555555555555555 \n";
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length, localhost, port);
            System.out.println("输出发送完毕\n");
            socket.send(datagramPacket);

            socket.close();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
