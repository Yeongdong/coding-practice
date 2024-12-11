package com.trading.stock_trading.market.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AlpacaWebSocketClient extends WebSocketListener {
    private final String apiKey;
    private final String secretKey;
    private WebSocket webSocket;

    public AlpacaWebSocketClient(
            @Value("${alpaca.api-key}") String apiKey,
            @Value("${alpaca.secret-key}") String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        connect();
    }

    private void connect() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("wss://stream.data.alpaca.markets/v2/iex")
                .build();
        webSocket = client.newWebSocket(request, this);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        log.info("WebSocket Connected");
        authenticate();
    }

    private void authenticate() {
        String authMessage = String.format("""
                {"action": "auth", "key": "%s", "secret": "%s"}
                """, apiKey, secretKey);
        webSocket.send(authMessage);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        log.info("Received: {}", text);
    }

    public void subscribe(String symbol) {
        String message = String.format("""
                {"action": "subscribe", "trades": ["%s"]}
                """, symbol);
        webSocket.send(message);
    }
}
