package com.myshop.order.command.domain;

import org.springframework.data.repository.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public interface OrderRepository extends Repository<Order, OrderNo> {
    // Id로 애그리거트 조회하기(null값을 사용하지 않기 위해 Optional을 사용한다)
    Optional<Order> findById(OrderNo id);

    // 애그리거트 저장하기
    void save(Order order);

    default OrderNo nextOrderNo() {
        int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new OrderNo(number);
    }
}
