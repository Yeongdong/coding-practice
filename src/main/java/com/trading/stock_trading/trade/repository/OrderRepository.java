package com.trading.stock_trading.trade.repository;

import com.trading.stock_trading.trade.entity.Order;
import com.trading.stock_trading.trade.entity.OrderStatus;

public interface OrderRepository {
    Order findById(String orderId);
    void save(Order order);
    void updateStatus(String orderId, OrderStatus status);
}
