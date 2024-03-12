package cn.lirui;

import cn.lirui.socket.MySocketClient;
import cn.lirui.socket.MySocketServer;

import java.io.IOException;

public class Main {
//    public static void main(String[] args) throws IOException {
//        MySocketClient mySocketClient = new MySocketClient();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mySocketClient.sendMessageFromKeyBoard();
//            }
//        }).start();
//
//        mySocketClient.receiveMessage();
//
//    }
    public static void main(String[] args) throws IOException {
        new MySocketServer();
    }
}