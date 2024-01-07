package com.myshop.order.domain;

import java.util.List;
import java.util.Objects;

public class Order {


    private OrderNo id;

    private Orderer orderer;

    private List<OrderLine> orderLines;
    private Money totalAmounts;
    private OrderState state;

    private ShippingInfo shippingInfo;

    public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo, OrderState orderState) {

        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    public void setOrderer(Orderer orderer) {
        if(orderer == null) {
            throw new IllegalArgumentException("no orderer");
        }
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        // 배송지 정보 필수 검증
        if(shippingInfo == null){
            throw new IllegalArgumentException("no ShippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }


    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no orderLine");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream().mapToInt(x -> x.getAmounts()).sum();
        this.totalAmounts = new Money(sum);
    }


    // 출고전에 배송지를 변경할수 있다
    // 주문 취소는 배송전에만 가능하다
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }

    private void verifyNotYetShipped() {
        if(state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING){
            throw new IllegalStateException("already shipped");
        }

    }

    private boolean isShippingChangeable() {
        return state == OrderState.PAYMENT_WAITING || state == OrderState.PREPARING;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        final  int prime = 31;
        int result = 1;
        result = prime * result  + ((orderNumber == null) ? 0 : orderNumber.hashCode());
        return result;
    }
}
