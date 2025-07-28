package com.trading.stock_trading.trade.dto;

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
    private String qty;            // 수량
    private String side;            // buy or sell
    private String type;            // market or limit

    @JsonProperty("time_in_force")
    private String timeInForce;   // day, gtc, ioc, fok
}
