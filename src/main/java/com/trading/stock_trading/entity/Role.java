package com.trading.stock_trading.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "REGULAR_USER"),
    ADMIN("ROLE_ADMIN", "ADMIN");

    private final String key;
    private final String title;
}
