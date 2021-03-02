package com.kithy.webfluxsampleapp.repository;

import com.kithy.webfluxsampleapp.model.Resource;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ResourceRepository extends ReactiveMongoRepository<Resource, String> {
}
