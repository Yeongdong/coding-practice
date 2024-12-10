package com.trading.stock_trading.trade.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String symbol;          // 종목 심볼
    private Integer qty;            // 수량
    private String side;            // buy or sell
    private String type;            // market or limit

    @JsonProperty("time_in_force")
    private String timeInForce;   // day, gtc, ioc, fok

    // 시장가 매수 주문 생성을 위한 정적 팩토리 메서드
    public static OrderRequest marketBuy(String symbol, int quantity) {
        return OrderRequest.builder()
                .symbol(symbol)
                .qty(quantity)
                .side("buy")
                .type("market")
                .timeInForce("day")
                .build();
    }

    // 시장가 매도 주문 생성을 위한 정적 팩토리 메서드
    public static OrderRequest marketSell(String symbol, int quantity) {
        return OrderRequest.builder()
                .symbol(symbol)
                .qty(quantity)
                .side("sell")
                .type("market")
                .timeInForce("day")
                .build();
    }
}
