package com.trading.stock_trading.market.service;

import com.trading.stock_trading.market.config.AlpacaWebSocketClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlpacaMarketDataService {
    private final AlpacaWebSocketClient webSocketClient;

    public void subscribeToStock(String symbol) {
        log.info("Subscribing to stock: {}", symbol);
        webSocketClient.subscribe(symbol);
    }
}
