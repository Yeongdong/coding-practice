package com.trading.stock_trading.repository;

import com.trading.stock_trading.entity.Stock;

import java.math.BigDecimal;

public interface StockRepository {
    Stock findBySymbol(String symbol);
    void save(Stock stock);
    void updatePrice(String symbol, BigDecimal newPrice);
}
