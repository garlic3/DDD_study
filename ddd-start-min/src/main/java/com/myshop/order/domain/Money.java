package com.myshop.order.domain;

// 데이터 변경 기능을 제공하지 않는다 (밸류 타입)
public class Money {

    private int value;

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Money add(Money money){
        return new Money(this.value + money.value);
    }

    public Money multiply(int multiplier){
        return new Money(value*multiplier);
    }
}
