package cn.lirui.aop.handler;

import cn.lirui.aop.TollFee;
import cn.lirui.util.YamlReader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-15:48:54
 */
public class TollGateHandler implements InvocationHandler {

    private TollFee vehicle;

    private HashMap<String, Object> enforcement;

    public TollGateHandler(TollFee vehicle) {
        this.vehicle = vehicle;
        this.enforcement = YamlReader.read("toll-station.yml");
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        System.out.println(enforcement.get("doBefore"));

        Object result = method.invoke(vehicle, objects);

        System.out.println("共收费"+ result +"元");

        System.out.println(enforcement.get("doAfter"));

        return result;
    }
}
