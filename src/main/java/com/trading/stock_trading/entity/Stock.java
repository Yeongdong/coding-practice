package com.trading.stock_trading.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Stock {
    private String symbol;
    private String name;
    private BigDecimal currentPrice;
    private long volume;
    private LocalDateTime lastUpdated;
}
