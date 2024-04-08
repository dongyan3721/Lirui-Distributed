package cn.lirui.aop;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-16:15:53
 */
public class Vehicle implements TollFee {
    @Override
    public double tollFee() {
        return 0;
    }
}


class Car implements TollFee{
    public double miles;

    public double perMileCost;
    @Override
    public double tollFee() {
        return miles*perMileCost;
    }

    public Car(double miles, double perMileCost) {
        this.miles = miles;
        this.perMileCost = perMileCost;
    }

    public Car() {
    }
}


class Bus implements TollFee{

    public int people;

    public double miles;

    public double perMileCost;
    @Override
    public double tollFee() {
        return miles*perMileCost;
    }

    public Bus(double miles, double perMileCost) {
        this.miles = miles;
        this.perMileCost = perMileCost;
    }

    public Bus(int people, double miles, double perMileCost) {
        this.people = people;
        this.miles = miles;
        this.perMileCost = perMileCost;
    }

    public Bus() {
    }
}

class Lorry implements TollFee{

    public double tons;

    public double miles;

    public double perMileCost;
    @Override
    public double tollFee() {
        return miles*perMileCost;
    }

    public Lorry(double tons, double miles, double perMileCost) {
        this.tons = tons;
        this.miles = miles;
        this.perMileCost = perMileCost;
    }

    public Lorry(double miles, double perMileCost) {
        this.miles = miles;
        this.perMileCost = perMileCost;
    }

    public Lorry() {
    }
}
