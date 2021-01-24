package com.kithy.webfluxsampleapp.controller;

import com.kithy.webfluxsampleapp.model.Resource;
import com.kithy.webfluxsampleapp.repository.ResourceRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping(path = "/resource", produces = MediaType.APPLICATION_STREAM_JSON_VALUE ) // "application/x-ndjson" zalecane zamiast  "application/stream+json"
@CrossOrigin(origins = "*")
public class ResourceApi {

    public final ResourceRepository repository;

    public ResourceApi(ResourceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Resource> get() {
        return repository.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/hello")
    public Flux<String> getString() {
        return Flux.just("Hello", " ", "World");
    }
}
