package com.trading.stock_trading.market.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastQuote {
    private String symbol;
    private Double bidPrice;      // 매수 호가
    private Double askPrice;      // 매도 호가
    private Long bidSize;         // 매수 수량
    private Long askSize;         // 매도 수량
    private LocalDateTime timestamp;
}
