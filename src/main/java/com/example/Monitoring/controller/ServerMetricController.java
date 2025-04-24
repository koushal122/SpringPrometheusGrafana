package com.example.Monitoring.controller;

import com.example.Monitoring.Entity.ServerMetric;
import com.example.Monitoring.service.ServerMetricService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class ServerMetricController {

    private final ServerMetricService service;
    //we create counter for creating custom metrics. this also we have created in GlobalExceptionHandler
    private final Counter requestCounter;

    public ServerMetricController(ServerMetricService service,
                                  MeterRegistry registry) {
        this.service = service;
        this.requestCounter = Counter
                .builder("api_requests_total")
                .description("Total API requests for metrics endpoints")
                .register(registry);
    }

    @PostMapping("/record")
    public ServerMetric recordMetric(@RequestParam String name,
                                     @RequestParam double value) {
        requestCounter.increment();
        return service.record(name, value);
    }

    @GetMapping
    public List<ServerMetric> listMetrics() {
        requestCounter.increment();
        return service.findAll();
    }

    @GetMapping("/illegal")
    public void errorEndpoint() {
        throw new IllegalStateException("Simulated server error Illegal");
    }

    @GetMapping("/runtime")
    public void errorEndpointRuntime() {
        throw new RuntimeException("Simulated server error Runtime");
    }

    //intentionally throwing exception to catch it on grafana
    @GetMapping("/nullpointer")
    public void errorEndpointNullpointer() {
        throw new RuntimeException("Simulated server error Runtime");
    }
}
