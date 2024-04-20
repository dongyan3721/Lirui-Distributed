package cn.lirui.rmi.client;

import cn.lirui.rmi.message.Message;
import cn.lirui.rmi.server.service.IDeposit;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/20-15:52:20
 */
public class RestaurantPos {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的学号>");
        String studentId = scanner.next();
        System.out.println("请输入您的消费金额，以正负号区分充值与消费>");
        double consume = scanner.nextDouble();
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        IDeposit deposit = (IDeposit)registry.lookup("deposit");
        Message consumed = deposit.consume(consume, studentId);
        if ((boolean)consumed.get(Message.CONSUME_FLAG)){
            if(consumed.containsKey(Message.MSG_TAG)){
                System.out.printf("%s当前余额%f元。\n", consumed.get(Message.MSG_TAG),((double) consumed.get(Message.REMAIN)));
            }else{
                System.out.printf("%s成功！当前余额%f元。\n", consume>0?"充值":"消费",((double) consumed.get(Message.REMAIN)));
            }
        }else{
            System.out.printf("消费失败！原因：%s，当前余额%f元。\n", consumed.get(Message.MSG_TAG), (double)consumed.get(Message.REMAIN));
        }
    }
}
