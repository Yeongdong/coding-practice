package com.trading.stock_trading.market.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BarData {
    private LocalDateTime timestamp;
    private Double open;    // 시가
    private Double high;    // 고가
    private Double low;     // 저가
    private Double close;   // 종가
    private Long volume;  // 거래량
    private Long tradeCount;    // 거래횟수
    private Double vwap;    // 거래량가중평균가격
}
