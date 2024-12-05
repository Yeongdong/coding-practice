package com.trading.stock_trading.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private String symbol;          // 종목 심볼
    private Integer quantity;       // 보유 수량
    private BigDecimal avgEntryPrice; // 평균 매수가
    private BigDecimal marketValue;   // 시장가치
    private BigDecimal currentPrice;  // 현재가
    private BigDecimal todayPl;       // 당일 손익
    private BigDecimal totalPl;       // 총 손익
    private BigDecimal plRate;        // 수익률(%)
    private LocalDateTime lastUpdated; // 마지막 업데이트 시간
}
