package com.trading.stock_trading.trade.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Order {
    private String orderId;
    private String symbol;
    private OrderType type;
    private BigDecimal price;
    private int quantity;
    private OrderStatus status;
    private LocalDateTime createdAt;

}
