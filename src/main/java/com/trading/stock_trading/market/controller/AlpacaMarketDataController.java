package com.trading.stock_trading.market.controller;

import com.trading.stock_trading.market.service.AlpacaMarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

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

    @GetMapping("/{symbol}/bars")
    public Flux<BarData> getHistoricalBars(
            @PathVariable String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "1Day") String timeframe) {
        return marketDataService.getHistoricalBars(symbol, start, end, timeframe);
    }


}
