package com.chethan.graphql.graphqldemospringboot.controller;

import com.chethan.graphql.graphqldemospringboot.model.User;
import com.chethan.graphql.graphqldemospringboot.service.UserService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class GraphQlController {
    private final UserService userService;
    @Autowired
    public GraphQlController(UserService myUserService){
        userService = myUserService;
    }

    @SchemaMapping(typeName = "Subscription", field = "subscribeToUser")
    public Publisher<User> subscribeToUser() {
        return userService.onUserCreated();
    }

    @SchemaMapping(typeName = "Mutation", field = "registerUser")
    public String registerUser(@Argument User userData) {
        String response = null;
        try {
            User createdUser = userService.createUser(userData);
            response = "Successfully saved user!";
        } catch (Exception e) {
            response = "Something went wrong! " + e.toString();
        }
        return response;
    }

    @SchemaMapping(typeName = "Query", field = "findAllUsers")
    public List<User> findAllUsers(){
        return userService.getAllUsers();
    }
}
