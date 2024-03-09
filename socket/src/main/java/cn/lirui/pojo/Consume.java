package cn.lirui.pojo;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/9-14:07:49
 */
public class Consume {
    private String userId;

    private double consume;

    public Consume(String userId, double consume) {
        this.userId = userId;
        this.consume = consume;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getConsume() {
        return consume;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }

    public Consume() {
    }
}
