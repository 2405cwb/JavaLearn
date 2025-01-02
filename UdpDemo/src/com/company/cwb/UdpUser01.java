package com.company.cwb;
//author:cwb 2025/1/2 16:31
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

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class UdpUser01 {
    private static volatile boolean running = true; // 标志位
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2); // 两个线程：一个接收，一个发送
        DatagramSocket receiveSocket = null;
        DatagramSocket sendSocket = null;
        AtomicReference<DatagramSocket> receiveSocketRef = new AtomicReference<>(); // 使用 AtomicRefere
        try {
            // 接收消息的线程
            executor.submit(() -> {
                try {
                    receiveSocketRef.set(new DatagramSocket(8888)); // 赋值给 AtomicReference
                    while (running) {
                        byte[] bytes = new byte[1024];
                        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
                        receiveSocketRef.get().receive(dp);

                        // 处理接收到的数据
                        String message = new String(dp.getData(), 0, dp.getLength(), StandardCharsets.UTF_8);
                        System.out.println(message);
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (receiveSocketRef.get()!= null && !receiveSocketRef.get().isClosed()) {
                        receiveSocketRef.get().close();
                        System.out.println("8888的接受任务结束");
                    }
                }
            });
            AtomicReference<DatagramSocket> sendSockettRef = new AtomicReference<>(); // 使用 AtomicRefere
            // 发送消息的线程
            executor.submit(() -> {
                try {
                    sendSockettRef.set( new DatagramSocket()); // 发送端不需要绑定端口
                    InetAddress serverAddress = InetAddress.getByName("localhost");
                    int serverPort = 8080;
                    System.out.println("开始聊天吧!");
                    Scanner scanner = new Scanner(System.in);
                    while (running) {
                        //System.out.print("请输入要发送的消息: ");
                        String inputLine = scanner.nextLine();
                        String line = "8888:"+inputLine;

                        // 发送消息
                        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, serverAddress, serverPort);
                        sendSockettRef.get().send(dp);
                        if (inputLine.equals("exit"))
                        {
                            // 等待线程池任务完成
                            running =false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (sendSockettRef.get() != null && !sendSockettRef.get().isClosed()) {
                        sendSockettRef.get().close();
                        System.out.println("8888的发送任务结束");
                    }
                }
            });

            executor.shutdown();
            while (!executor.isTerminated()) {
                // 等待所有任务完成
            }
            System.out.println("8888任务结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (receiveSocket != null && !receiveSocket.isClosed()) {
                receiveSocket.close();
            }
            if (sendSocket != null && !sendSocket.isClosed()) {
                sendSocket.close();
            }
            executor.shutdownNow(); // 强制关闭线程池
        }
    }



}
