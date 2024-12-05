package com.trading.stock_trading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AlpacaConfig {
    @Value("${alpaca.api-key}")
    private String apiKey;

    @Value("${alpaca.secret-key}")
    private String secretKey;

    @Value("${alpaca.base-url}")
    private String baseUrl;

    @Bean
    public WebClient alpacaWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("APCA-API-KEY-ID", apiKey)
                .defaultHeader("APCA-API-SECRET-KEY", secretKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
