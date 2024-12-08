package com.trading.stock_trading.trade.controller;

import com.trading.stock_trading.trade.service.StockTradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StockTradingController {
    private final StockTradingService stockTradingService;

}
