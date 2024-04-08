package cn.lirui.aop;

import cn.lirui.aop.factory.VehicleFactory;
import cn.lirui.aop.handler.TollGateHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-16:55:22
 */
public class TollGate {


    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TollGate tollGate = new TollGate();
        tollGate.charge();
    }


    public void charge() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TollFee vehicle = VehicleFactory.generateVehicle();
        TollGateHandler handler = new TollGateHandler(vehicle);
        TollFee proxyInstance = (TollFee)Proxy.newProxyInstance(vehicle.getClass().getClassLoader(),
                vehicle.getClass().getInterfaces(), handler);
        proxyInstance.tollFee();
    }
}
