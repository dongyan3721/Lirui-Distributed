package cn.lirui.socket;

import cn.lirui.pojo.Consume;
import com.alibaba.fastjson2.JSON;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Santa Antilles
 * @description socket客户端
 * @date 2024/3/9-15:33:40
 */
public class MySocketClient {
    private static final int PORT = 8086;

    private static final String HOST = "192.168.124.6";

    private Socket socket;

    private String userId;


    public MySocketClient() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("client: 请输入您的学号以构建连接>");
        this.userId = scanner.next();
        this.socket = new Socket(HOST, PORT);

        Consume consume = new Consume();
        consume.setUserId(this.userId);

        sendMessage(consume);

        System.out.printf("server: %s", extractMessage());
    }

    private void sendMessage(Consume consume){
        PrintWriter printWriter ;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printWriter.println(JSON.toJSON(consume));

        printWriter.flush();

        printWriter.close();

    }

    public void sendMessageFromKeyBoard(){
        while(true){
            System.out.print("client: 请输入消费或充值金额(以正负号标识)>");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            double consume;
            try {
                consume = Double.parseDouble(line);
                sendMessage(new Consume(this.userId, consume));
            }catch (Exception e){
                System.out.println("client: 输入有误，请重新输入");
            }
        }
    }

    // 监听服务端的信息
    public void receiveMessage(){
        while (true){

            String messageFromServer = extractMessage();

            if (!messageFromServer.isEmpty()) System.out.printf("server: %s", messageFromServer);

            if (messageFromServer.contains("退出")) break;

        }
        closeClient();
    }


    private String extractMessage(){
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String messageFromServer;
        try {
            messageFromServer  = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return messageFromServer;
    }


    private void closeClient(){
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
