package com.tb.fund.entity;

public class Cart {
    private Money money;
    private Integer number;

    public Cart() {
    }

    public Cart(Money money, Integer number) {

        this.money = money;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "money=" + money +
                ", number=" + number +
                '}';
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
