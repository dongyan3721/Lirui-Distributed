package cn.lirui.ioc;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-13:56:08
 */
public interface Food {
    void eat();
}

class Beef implements Food {

    @Override
    public void eat() {
        System.out.println("吃牛排...");
    }
}

class Salad implements Food{

    @Override
    public void eat() {
        System.out.println("吃沙拉...");
    }
}