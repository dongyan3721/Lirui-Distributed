package cn.lirui.rmi.client;

import cn.lirui.rmi.message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/20-16:05:15
 */
public interface IDeposit extends Remote {
    public Message consume(double amount, String studentId) throws RemoteException;
}
