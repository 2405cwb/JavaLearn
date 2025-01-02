package com.company.cwb;
//author:cwb 2025/1/2 15:58
//Administrator
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//              佛祖保佑       永无BUG     永不修改                  //

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerDemo {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8080);
            while (true)
            {
                byte[] buffer = new byte[5];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                int i = 0 ;
                while (true) {
                    socket.receive(packet);
                    if ( packet.getLength()!=0 &&( packet.getLength() == buffer.length)) {
                        String temp = new String(buffer, 0, packet.getLength());
                        System.out.println("收到消息: " +i++ +":"+ temp);
                        // 数据可能被截断，增大缓冲区大小
                        buffer = new byte[buffer.length * 2];
                        packet = new DatagramPacket(buffer, buffer.length);


                        continue;
                    }

                    // 处理接收到的数据
                    String receivedMessage = new String(buffer, 0, packet.getLength());
                    System.out.println("收到消息: " + receivedMessage);
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
