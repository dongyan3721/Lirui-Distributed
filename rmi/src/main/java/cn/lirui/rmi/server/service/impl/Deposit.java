package cn.lirui.rmi.server.service.impl;

import cn.lirui.pojo.Card;
import cn.lirui.rmi.message.Message;
import cn.lirui.rmi.server.service.IDeposit;
import cn.lirui.util.CardIdGenerator;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/20-16:07:06
 */
public class Deposit extends UnicastRemoteObject implements IDeposit {

    private ConcurrentHashMap<String, Card> deposit;

    public Deposit() throws RemoteException {
        this.deposit = loadUserDeposit();
    }


    @Override
    public Message consume(double amount, String studentId) {
        System.out.println("当前连接学生"+studentId);
        System.out.println("学生操作："+(amount>0?"充值":"消费")+amount+"元");
        if (deposit.containsKey(studentId)){
            // 充值消费操作
            double left = deposit.get(studentId).getLeft();
            // 读写操作之后保存到磁盘
            if (amount <= 0){
                left = left + amount;
                if (left >=0){
                    deposit.get(studentId).setLeft(left);
                    try {
                        serializeUserDeposit(deposit);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("操作成功！卡账户"+deposit.get(studentId).getCardId()+String.format("剩余余额%f元", left));
                    return Message.success(left);
                }else{
                    System.out.println("操作失败，余额不足！");
                    return Message.error(deposit.get(studentId).getLeft(), "余额不足！");
                }
            }else{
                deposit.get(studentId).setLeft(left+amount);
                try {
                    serializeUserDeposit(deposit);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("操作成功！卡账户"+deposit.get(studentId).getCardId()+String.format("剩余余额%f元", left+amount));
                return Message.success(left+amount);
            }

        }else{
            Card card = new Card();
            String cardId = String.valueOf(CardIdGenerator.generateId(1L, 1L));
            card.setCardId(cardId);
            amount = amount>0?amount:0;
            card.setLeft(amount);
            deposit.put(studentId, card);
            try {
                serializeUserDeposit(deposit);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return amount>0?Message.success(amount, String.format("学号为%s的同学您好，目前还没有您的卡记录，已经为您创建卡" +
                    "\"%s\"，请妥善保存", studentId, cardId)):Message.error(amount, String.format("学号为%s的同学您好，目前还" +
                    "没有您的卡记录，已经为您创建卡\"%s\"，请妥善保存", studentId, cardId));
        }
    }

    /**
     * 加载已有存款信息
     * @return
     */
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

    /**
     *
     * @param deposit
     * @throws IOException
     */
    private void serializeUserDeposit(ConcurrentHashMap<String, Card> deposit) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("deposit.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(deposit);
        out.close();
        fileOut.close();
    }




}
