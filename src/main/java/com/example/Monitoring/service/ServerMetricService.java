package com.example.Monitoring.service;

import com.example.Monitoring.Entity.ServerMetric;
import com.example.Monitoring.repository.ServerMetricRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ServerMetricService {

    private final ServerMetricRepository repository;

    public ServerMetricService(ServerMetricRepository repository) {
        this.repository = repository;
    }

    public ServerMetric record(String name, double value) {
        ServerMetric metric = new ServerMetric();
        metric.setMetricName(name);
        metric.setValue(value);
        metric.setTimestamp(Instant.now());
        return repository.save(metric);
    }

    public List<ServerMetric> findAll() {
        return repository.findAll();
    }
}
