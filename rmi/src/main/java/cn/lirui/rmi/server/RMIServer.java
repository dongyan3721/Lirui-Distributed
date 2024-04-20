package cn.lirui.rmi.server;

import cn.lirui.rmi.server.service.impl.Deposit;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/20-15:51:59
 */
public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Deposit deposit = new Deposit();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("deposit", deposit);
        System.out.println("RMI服务已启动...");
    }
}
