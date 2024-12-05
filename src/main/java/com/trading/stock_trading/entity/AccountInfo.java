package com.trading.stock_trading.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    private String id;                  // 계좌 ID
    private String accountNumber;       // 계좌 번호
    private String status;              // 계좌 상태 (ACTIVE, INACTIVE 등)
    private String currency;            // 기준 통화 (USD)
    private BigDecimal cash;            // 보유 현금
    private BigDecimal portfolioValue;  // 포트폴리오 총 가치
    private BigDecimal buyingPower;     // 매수 가능 금액
    private Boolean tradeSuspended;     // 거래 정지 여부
    private Boolean transfersBlocked;   // 이체 차단 여부
    private String accountBlocked;      // 계좌 차단 여부
    private LocalDateTime createdAt;    // 계좌 생성일
}
