package cn.lirui.ioc;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-13:58:21
 */
public class FoodFactory {
    public static Object generateFood(String foodName){
        try {
            Class<?> foodClass = Class.forName("cn.lirui.ioc." + foodName);
           return foodClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
