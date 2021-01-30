package com.kithy.webfluxsampleapp.service;

import com.kithy.webfluxsampleapp.model.Resource;
import com.kithy.webfluxsampleapp.repository.ResourceRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class InitService {

    private final ResourceRepository repository;

    public InitService(ResourceRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        repository.deleteAll()
                .thenMany(
                        Flux.just("{\"resourceType\" : \"Patient\"}", "{\"resourceType\" : \"Observation\"}", "{\"resourceType\" : \"Patient\"}", "{\"resourceType\" : \"Observation\"}")
                ).map(Resource::new)
                .flatMap(repository::save)
                .thenMany(repository.findAll())
                .subscribe(System.out::println);

    }
}
