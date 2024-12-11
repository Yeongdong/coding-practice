package com.trading.stock_trading.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("client_order_id")
    private String clientOrderId;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    @JsonProperty("submitted_at")
    private OffsetDateTime submittedAt;

    @JsonProperty("filled_at")
    private OffsetDateTime filledAt;

    @JsonProperty("expired_at")
    private OffsetDateTime expiredAt;

    @JsonProperty("canceled_at")
    private OffsetDateTime canceledAt;

    @JsonProperty("failed_at")
    private OffsetDateTime failedAt;

    @JsonProperty("asset_id")
    private UUID assetId;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("asset_class")
    private String assetClass;

    @JsonProperty("qty")
    private String qty;

    @JsonProperty("filled_qty")
    private String filledQty;

    @JsonProperty("filled_avg_price")
    private String filledAvgPrice;

    @JsonProperty("type")
    private String type;

    @JsonProperty("side")
    private String side;

    @JsonProperty("time_in_force")
    private String timeInForce;

    @JsonProperty("limit_price")
    private String limitPrice;

    @JsonProperty("stop_price")
    private String stopPrice;

    @JsonProperty("status")
    private String status;
}
