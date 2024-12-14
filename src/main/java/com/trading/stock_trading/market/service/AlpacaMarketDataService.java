package com.trading.stock_trading.market.service;

import com.trading.stock_trading.market.config.AlpacaWebSocketClient;
import com.trading.stock_trading.market.dto.BarData;
import com.trading.stock_trading.market.dto.LastQuote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<LastQuote> getLastQuote(String symbol) {
        return webClient.get()
                .uri("/v2/stocks/{symbol}/quotes/latest", symbol)
                .retrieve()
                .bodyToMono(LastQuote.class)
                .doOnError(error -> log.error("Failed to get last quote for {}", symbol, error));
    }
}
