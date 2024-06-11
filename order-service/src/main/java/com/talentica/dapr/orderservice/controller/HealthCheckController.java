package com.talentica.dapr.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app/health")
@Slf4j
public class HealthCheckController {

    @GetMapping
    public String status() {
        return "ok";
    }
}
