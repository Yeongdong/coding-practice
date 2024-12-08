package com.trading.stock_trading.trade.service;

import com.trading.stock_trading.trade.entity.Order;
import com.trading.stock_trading.trade.entity.OrderType;
import com.trading.stock_trading.trade.entity.Stock;
import com.trading.stock_trading.trade.repository.OrderRepository;
import com.trading.stock_trading.trade.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

import static com.trading.stock_trading.trade.entity.OrderStatus.*;
import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class StockTradingService {
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final Random random = new Random();

    public Stock getStockInfo(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol);
        if (stock == null) {
            throw new RuntimeException("Stock not found: " + symbol);
        }
        return stock;
    }

    public Order plicaeOrder(String symbol, OrderType orderType, int quantity) {
        Stock stock = getStockInfo(symbol);
        String orderId = UUID.randomUUID().toString();

        Order order = new Order(orderId, symbol, orderType, stock.getCurrentPrice(), quantity, PENDING, now());
        orderRepository.save(order);

        return order;
    }

    public Order getOrder(String orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found: " + orderId);
        }
        return order;
    }
}
