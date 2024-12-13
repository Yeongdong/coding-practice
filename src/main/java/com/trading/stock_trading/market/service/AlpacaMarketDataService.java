package com.trading.stock_trading.market.service;

import com.trading.stock_trading.market.config.AlpacaWebSocketClient;
import com.trading.stock_trading.market.controller.BarData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlpacaMarketDataService {
    private final WebClient webClient;
    private final AlpacaWebSocketClient webSocketClient;

    public Flux<BarData> getHistoricalBars(String symbol, LocalDateTime start, LocalDateTime end, String timeframe) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/stocks/{symbol}/bars")
                        .queryParam("start", start.toString())
                        .queryParam("end", end.toString())
                        .queryParam("timeframe", timeframe)
                        .build(symbol))
                .retrieve()
                .bodyToFlux(BarData.class)
                .doOnError(error -> log.error("Failed to get historical bars", error));
    }

    public void subscribeToStock(String symbol) {
        log.info("Subscribing to stock: {}", symbol);
        webSocketClient.subscribe(symbol);
    }
}
