package com.easytrade.server.demo;

import com.easytrade.server.config.EasyTradeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v1/demo")
@RequiredArgsConstructor
public class DemoController {
    private final EasyTradeProperties easyTradeProperties;

    @GetMapping
    public ResponseEntity<?> sayHi() throws IOException {
        String[] symbols = easyTradeProperties.getSymbols();
        var stocks = YahooFinance.get(symbols);
        String stockList = stocks.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue().getQuote().toString()))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok("Hi, you must be logged in to see this! The stock exchange we use is " + easyTradeProperties.getExchange() +
                                 ". The symbols we track are " + String.join("; ", easyTradeProperties.getSymbols()) +
                                                                                   ". Here's some more data:\n" + stockList
        );
    }
}
