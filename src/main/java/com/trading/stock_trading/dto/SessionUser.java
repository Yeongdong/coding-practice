package com.trading.stock_trading.dto;

import com.trading.stock_trading.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
