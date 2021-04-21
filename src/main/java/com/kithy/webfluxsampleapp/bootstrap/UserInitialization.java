package com.kithy.webfluxsampleapp.bootstrap;

import com.kithy.webfluxsampleapp.model.user.User;
import com.kithy.webfluxsampleapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@ConditionalOnProperty(name = "user.bootstrap.enabled", havingValue = "true")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class UserInitialization implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    public UserInitialization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void prepareAndSaveUsers() {
        User saved;
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");

        userRepository.deleteAll()
                .thenMany(
                        Flux.just(user1)
                ).flatMap(userRepository::save)
                .thenMany(userRepository.findAll())
                .subscribe(System.out::println);

    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        prepareAndSaveUsers();
    }
}
