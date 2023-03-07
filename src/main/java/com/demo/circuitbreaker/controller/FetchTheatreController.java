package com.demo.circuitbreaker.controller;

import com.demo.circuitbreaker.service.FetchTheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FetchTheatreController {

    @Autowired
    FetchTheatreService fetchTheatreService;

    @RequestMapping("/fetch-theatre-list")
    public Mono<String> fetchTheatreList(){
        return fetchTheatreService.fetchTheatreList();
    }
}
