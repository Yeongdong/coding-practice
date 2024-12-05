package com.trading.stock_trading.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;              // 주문 ID
    private String clientOrderId;   // 클라이언트 주문 ID
    private String symbol;          // 종목 심볼
    private BigDecimal quantity;    // 주문 수량
    private String side;            // 매수/매도 구분 (buy/sell)
    private String type;            // 주문 유형 (market/limit 등)
    private BigDecimal limitPrice;  // 지정가 주문시 가격
    private String status;          // 주문 상태
    private LocalDateTime createdAt;// 주문 생성 시간
    private LocalDateTime filledAt; // 체결 시간
    private BigDecimal filledPrice; // 체결 가격
    private String timeInForce;     // 주문 유효 기간
}
