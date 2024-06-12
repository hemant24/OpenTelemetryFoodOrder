package com.talentica.dapr.orderservice.controller;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.DoubleGaugeBuilder;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/cm")
@Slf4j
public class CustomMetricController {


    private final Meter meter = GlobalOpenTelemetry.meterBuilder("io.opentelemetry.metrics.hello")
            .setInstrumentationVersion("1.0.0")
            .build();

    private LongCounter numberOfExecutions;


    @PostConstruct
    public void init(){
        numberOfExecutions = meter.counterBuilder("custom.metric.number.of.exec")
                .setDescription("Count the number of execution")
                .setUnit("int")
                .build();

        meter.gaugeBuilder("custom.metric.heap.memory")
                .setDescription("memory used")
                .setUnit("byte")
                .buildWithCallback(r -> {
                    r.record(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
                });
    }

    @GetMapping("/counter")
    public String count() {
        numberOfExecutions.add(1);
        return "done";
    }
}
