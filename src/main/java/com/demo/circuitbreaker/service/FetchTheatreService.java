package com.demo.circuitbreaker.service;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;

@Service
public class FetchTheatreService {
    private static final Logger LOG = LoggerFactory.getLogger(FetchTheatreService.class);
    private final WebClient webClient;

    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    public FetchTheatreService(ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory){
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        this.reactiveCircuitBreaker = reactiveCircuitBreakerFactory.create("test");
    }

    public Mono<String> fetchTheatreList(){
        final Mono<String> using_response_from_circuit_breaker = reactiveCircuitBreaker.run(webClient.get().uri("/api/test/fetch-theatre-list").retrieve().bodyToMono(String.class), throwable -> {
            LOG.error("Error while making request to fetch theatre list: " + throwable);
            return Mono.just("Using response from Circuit breaker");
        });
        LOG.info ("I am being executed before the response is received from other service");
        return using_response_from_circuit_breaker;
    }
}
