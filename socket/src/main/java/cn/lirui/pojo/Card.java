package cn.lirui.pojo;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/9-13:18:19
 */
public class Card {
    private String cardId;

    private double left;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public Card(String cardId, double left) {
        this.cardId = cardId;
        this.left = left;
    }

    public Card() {
    }



}
