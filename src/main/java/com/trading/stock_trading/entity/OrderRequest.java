package com.trading.stock_trading.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String symbol;          // 종목 심볼
    private Integer quantity;       // 주문 수량
    private String side;            // buy 또는 sell
    private String type;            // market, limit 등
    private BigDecimal limitPrice;  // 지정가 주문시 가격
    private String timeInForce;     // day, gtc 등

    // 시장가 매수 주문 생성을 위한 정적 팩토리 메서드
    public static OrderRequest marketBuy(String symbol, int quantity) {
        return OrderRequest.builder()
                .symbol(symbol)
                .quantity(quantity)
                .side("buy")
                .type("market")
                .timeInForce("day")
                .build();
    }

    // 시장가 매도 주문 생성을 위한 정적 팩토리 메서드
    public static OrderRequest marketSell(String symbol, int quantity) {
        return OrderRequest.builder()
                .symbol(symbol)
                .quantity(quantity)
                .side("sell")
                .type("market")
                .timeInForce("day")
                .build();
    }
}
