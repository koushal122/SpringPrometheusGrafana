package com.example.Monitoring.exceptions;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MeterRegistry meterRegistry;

    public GlobalExceptionHandler(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    //whatever exception comes will be handled by this method.
    //if we have multiple exceptions method then in that case
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleAllExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        // Create or retrieve a counter with detailed tags
        Counter counter = Counter.builder("api_exceptions_total")
                .description("Total API exceptions with details")
                .tag("exception", ex.getClass().getSimpleName())
                .tag("uri", request.getRequestURI())
                .tag("method", request.getMethod())
                .tag("message", ex.getMessage())  // note: high cardinality risk
                .register(meterRegistry);
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURI());
        counter.increment();

    }
}
