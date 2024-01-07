package com.myshop.order.domain;

public class OrderLine {

    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;

    public OrderLine(Product product, int price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    // 구매 가격 메소드
    private Money calculateAmounts() {
        return price.multiply(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Money getAmounts() {
        return amounts;
    }

    public void setAmounts(Money amounts) {
        this.amounts = amounts;
    }
}
