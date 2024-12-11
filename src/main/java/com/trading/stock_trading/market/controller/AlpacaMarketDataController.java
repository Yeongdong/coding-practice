package com.trading.stock_trading.market.controller;

import com.trading.stock_trading.market.service.AlpacaMarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alpaca/market-data")
public class AlpacaMarketDataController {
    private final AlpacaMarketDataService marketDataService;

    @PostMapping("/subscribe/{symbol}")
    public ResponseEntity<Void> subscribe(@PathVariable String symbol) {
        marketDataService.subscribeToStock(symbol);
        return ResponseEntity.ok().build();
    }
}
