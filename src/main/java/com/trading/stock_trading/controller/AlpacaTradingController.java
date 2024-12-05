package com.trading.stock_trading.controller;

import com.trading.stock_trading.entity.*;
import com.trading.stock_trading.service.AlpacaTradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/alpaca")
@RequiredArgsConstructor
public class AlpacaTradingController {
    private final AlpacaTradingService tradingService;

    @GetMapping("/account")
    public Mono<AccountInfo> getAccount() {
        return tradingService.getAccount();
    }

    @PostMapping("/orders")
    public Mono<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        return tradingService.placeOrder(orderRequest);
    }

    @GetMapping("/positions")
    public Flux<Position> getPositions() {
        return tradingService.getPositions();
    }

    @GetMapping("/orders")
    public Flux<Order> getOrders() {
        return tradingService.getOrders();
    }
}
