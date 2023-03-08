package com.easytrade.server.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
public class DemoController {
    @GetMapping
    public ResponseEntity<?> sayHi() {
        return ResponseEntity.ok("Hi, you must be logged in to see this!");
    }
}
