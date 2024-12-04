package com.trading.stock_trading.entity;

import lombok.Data;

@Data
public class OrderRequest {
    private String symbol;
    private OrderType type;
    private int quantity;
}
