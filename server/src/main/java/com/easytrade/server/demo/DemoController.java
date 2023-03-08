package com.easytrade.server.demo;

import com.easytrade.server.config.EasyTradeProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/demo")
public class DemoController {
    private final EasyTradeProperties properties;

    public DemoController(EasyTradeProperties properties) {
        this.properties = properties;
    }


    @GetMapping
    public ResponseEntity<?> sayHi() {

        return ResponseEntity.ok("Hi, you must be logged in to see this! The market is " + properties.getMarket() +
                                 " symbols are " + String.join("; ", properties.getSymbols())
        );
    }
}
