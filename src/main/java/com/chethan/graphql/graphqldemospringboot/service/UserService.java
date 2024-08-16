package com.chethan.graphql.graphqldemospringboot.service;

import com.chethan.graphql.graphqldemospringboot.model.User;
import com.chethan.graphql.graphqldemospringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Sinks.Many<User> userCreatedSink;

    @Autowired
    public UserService(UserRepository myUserRepository) {
        this.userRepository = myUserRepository;
        this.userCreatedSink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        User createdUser = userRepository.save(user);
        // Emit an event when a new user is created
        userCreatedSink.tryEmitNext(createdUser);
        return createdUser;
    }

    public Flux<User> onUserCreated() {
        return userCreatedSink.asFlux();
    }
}
