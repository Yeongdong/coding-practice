package com.trading.stock_trading.repository;

import com.trading.stock_trading.entity.Order;
import com.trading.stock_trading.entity.OrderStatus;

public interface OrderRepository {
    Order findById(String orderId);
    void save(Order order);
    void updateStatus(String orderId, OrderStatus status);
}
