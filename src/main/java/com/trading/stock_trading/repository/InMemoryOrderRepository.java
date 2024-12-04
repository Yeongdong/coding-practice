package com.trading.stock_trading.repository;

import com.trading.stock_trading.entity.Order;
import com.trading.stock_trading.entity.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orderDatabase = new HashMap<>();

    @Override
    public Order findById(String orderId) {
        return orderDatabase.get(orderId);
    }

    @Override
    public void save(Order order) {
        orderDatabase.put(order.getOrderId(), order);
    }

    @Override
    public void updateStatus(String orderId, OrderStatus status) {
        Order order = orderDatabase.get(orderId);
        if (order != null) {
            order.setStatus(status);
        }
    }
}
