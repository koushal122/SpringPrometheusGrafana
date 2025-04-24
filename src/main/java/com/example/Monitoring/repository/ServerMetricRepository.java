package com.example.Monitoring.repository;

import com.example.Monitoring.Entity.ServerMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerMetricRepository extends JpaRepository<ServerMetric, Long> {
}
