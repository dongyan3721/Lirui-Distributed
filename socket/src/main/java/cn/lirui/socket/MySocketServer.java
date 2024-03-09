package cn.lirui.socket;



import cn.lirui.pojo.Card;
import cn.lirui.pojo.Consume;
import cn.lirui.util.CardIdGenerator;
import com.alibaba.fastjson2.JSON;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Santa Antilles
 * @description socket服务器实现
 * @date 2024/3/9-11:50:43
 */
public class MySocketServer {

    private ConcurrentHashMap<String, Card> deposit;

    private ExecutorService threadPool;

    private ServerSocket server;

    private static final int PORT = 8086;

    public static void main(String[] args) throws IOException {
        new MySocketServer();
    }


    public MySocketServer() throws IOException {
        this.deposit = loadUserDeposit();
        this.threadPool = Executors.newFixedThreadPool(5);
        this.server = new ServerSocket(PORT);
        System.out.println(this.deposit);
        System.out.println("server: socket服务已启动>");
        while(true){
            Socket clientSocket = server.accept();
            SocketRequestHandler innerThread = new SocketRequestHandler(clientSocket);
            threadPool.execute(innerThread);
        }
    }

    private ConcurrentHashMap<String, Card> loadUserDeposit(){
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream("deposit.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ConcurrentHashMap<String, Card> user = (ConcurrentHashMap<String, Card>) in.readObject();
            return user;
        } catch (IOException | ClassNotFoundException e) {
            return new ConcurrentHashMap<>();
        }
    }


    private void serializeUserDeposit(ConcurrentHashMap<String, Card> deposit) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("deposit.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(deposit);
        out.close();
        fileOut.close();
    }

    private class SocketRequestHandler implements Runnable{
        private Socket request;
        @Override
        public void run() {
            BufferedReader br;
            PrintWriter pw;
            try {
                br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                pw = new PrintWriter(new OutputStreamWriter(request.getOutputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 记录是否第一次连上
            int cnt = 0;

            while(true){
                String input;
                try {
                    input = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (input.isEmpty()) continue;
                if (input.equals("close")) break;

                Consume consume = JSON.parseObject(input, Consume.class);


                // 首次登录，给提示
                if (cnt == 0) {
                    if (deposit.containsKey(consume.getUserId())){
                        pw.println(String.format("欢迎学号为%s的同学登录，您的卡内剩余余额为%f元\n",
                                consume.getUserId(), deposit.get(consume.getUserId()).getLeft()));
                        pw.flush();
                    }else{
                        Card card = new Card();
                        String cardId = String.valueOf(CardIdGenerator.generateId(1L, 1L));
                        card.setCardId(cardId);
                        card.setLeft(0F);
                        deposit.put(consume.getUserId(), card);
                        pw.println(String.format("欢迎学号为%s的同学登录，您还没有卡号，已为您创建卡号为%s的储蓄卡，卡内余额%f元\n",
                                consume.getUserId(), cardId, 0F));
                        pw.flush();
                    }
                    ++cnt;
                    continue;
                }

                // 充值消费操作
                String userId = consume.getUserId();
                double consumeOrDeposit = consume.getConsume();
                double left = deposit.get(userId).getLeft();
                if (consumeOrDeposit <= 0){
                    left = left + consumeOrDeposit;
                    if (left >=0){
                        deposit.get(userId).setLeft(left);
                        pw.println(String.format("消费成功！卡内余额%f元\n", left));
                        pw.flush();
                    }else{
                        pw.println("消费失败，卡内余额不足！\n");
                        pw.flush();
                    }
                }else{
                    deposit.get(userId).setLeft(left+consumeOrDeposit);
                    pw.println(String.format("充值成功！卡内余额%f元\n", left+consumeOrDeposit));
                    pw.flush();
                }


            }
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pw.println("用户退出，连接已关闭");
            pw.flush();
            pw.close();
//            try {
//                request.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            try {
                serializeUserDeposit(deposit);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public SocketRequestHandler(Socket request) {
            this.request = request;
        }
    }
}
