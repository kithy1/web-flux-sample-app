package com.kithy.webfluxsampleapp.service;

import com.kithy.webfluxsampleapp.model.user.MyUserPrincipal;
import com.kithy.webfluxsampleapp.model.user.User;
import com.kithy.webfluxsampleapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.Filter;


@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Flux<User> all = userRepository.findAll();
        Mono<User> userMono = all.all(user -> user.getUsername().equals(username)).cast(User.class);
        User user = userMono.block();
        log.debug("loaded user: {}", user);


        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
