package com.easytrade.server.demo;

import com.easytrade.server.config.EasyTradeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v1/demo")
@RequiredArgsConstructor
public class DemoController {
    private final EasyTradeProperties easyTradeProperties;

    @GetMapping
    public ResponseEntity<?> sayHi() throws IOException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -3); // from 3 months ago
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Stock google = YahooFinance.get("GOOG", from, to, Interval.WEEKLY);
        String historyOfGoogle = google.getHistory().stream()
                .map(hq -> String.format("Date: %s | High: %s, Low: %s, Open: %s, Close: %s",
                        fmt.format(hq.getDate().getTime()),
                        hq.getHigh().toString(),
                        hq.getLow().toString(),
                        hq.getOpen().toString(),
                        hq.getClose().toString()))
                .collect(Collectors.joining("\n"));

        String[] symbols = easyTradeProperties.getSymbols();
        var stocks = YahooFinance.get(symbols, from, to, Interval.WEEKLY);
        String stockList = stocks.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue().getQuote().toString()))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok("Hi, you must be logged in to see this! The stock exchange we use is " + easyTradeProperties.getExchange() +
                                 ". The symbols we track are " + String.join("; ", easyTradeProperties.getSymbols()) +
                                                                                   ". Here's some more data:\n" + stockList + "\n\n" +
                "Here's a three month history of google's stock:\n" + historyOfGoogle
        );
    }
}
