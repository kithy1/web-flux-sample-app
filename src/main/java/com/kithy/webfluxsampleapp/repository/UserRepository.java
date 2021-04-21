package com.kithy.webfluxsampleapp.repository;

import com.kithy.webfluxsampleapp.model.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {

}
