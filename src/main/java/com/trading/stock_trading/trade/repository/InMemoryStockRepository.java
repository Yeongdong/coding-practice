package com.trading.stock_trading.trade.repository;

import com.trading.stock_trading.trade.entity.Stock;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryStockRepository implements StockRepository {
    private final Map<String, Stock> stockDatabase = new HashMap<>();

    @PostConstruct
    public void init() {
        stockDatabase.put("005930", new Stock("005930", "삼성전자", new BigDecimal("72000"), 1000000L, LocalDateTime.now()));
        stockDatabase.put("035720", new Stock("035720", "카카오", new BigDecimal("55000"), 500000L, LocalDateTime.now()));
    }

    @Override
    public Stock findBySymbol(String symbol) {
        return stockDatabase.get(symbol);
    }

    @Override
    public void save(Stock stock) {
        stockDatabase.put(stock.getSymbol(), stock);
    }

    @Override
    public void updatePrice(String symbol, BigDecimal newPrice) {
        Stock stock = stockDatabase.get(symbol);
        if (stock != null) {
            stock.setCurrentPrice(newPrice);
            stock.setLastUpdated(LocalDateTime.now());
        }
    }
}
