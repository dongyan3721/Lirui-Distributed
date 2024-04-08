package cn.lirui.ioc;

import java.util.Scanner;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-13:57:28
 */
public class Restaurant {
    Food f;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("点菜...");
        String foodName = scanner.next();
        Restaurant restaurant = new Restaurant();
        restaurant.order(foodName);
    }

    public void order(String foodName){
        System.out.println("客人点了一份"+foodName);
        Object food = FoodFactory.generateFood(foodName);
        if (food instanceof Food){
            f = (Food) food;
        }
        f.eat();
    }

}