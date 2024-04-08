package cn.lirui.aop.factory;

import cn.lirui.aop.TollFee;
import cn.lirui.util.YamlReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-15:54:20
 */
public class VehicleFactory {
    public static TollFee generateVehicle() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入交通工具名...");
        String vehicleName = scanner.next();
        HashMap<String, Object> properties = YamlReader.read("properties.yml");
        HashMap<String, Object> vehicles = (HashMap<String, Object>) properties.get("vehicles");
        if (!vehicles.containsKey(vehicleName)) throw new RuntimeException("类不存在！");
        /**
         * 这里读取yaml文件内的内容，计算出每公里计费情况
         */
        // 这里简化读配置文件了，懒一下
        HashMap<String, Object> detailVehicle = (HashMap<String, Object>) vehicles.get(vehicleName);



        boolean mileOnly = (boolean)detailVehicle.get("mileOnly");
        double perMileCost = mileOnly?((double)detailVehicle.get("perMile")):1.2;
        System.out.println("输入行驶里程...");
        double miles = scanner.nextDouble();
        /**
         * 此次其实还需要针对配置文件中的类其他属性进行键盘输入的设置
         */

        /**
         * 此处用反射构建类
         */
        Class<?> vehicleClazz = Class.forName("cn.lirui.aop." + vehicleName);

        Constructor<?> plainConstructor = vehicleClazz.getConstructor(double.class, double.class);
        plainConstructor.setAccessible(true);
        return (TollFee) plainConstructor.newInstance(miles, perMileCost);
    }
}
