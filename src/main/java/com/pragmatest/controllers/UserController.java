package com.pragmatest.controllers;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pragmatest.exceptions.UserInvalidException;
import com.pragmatest.models.User;
import com.pragmatest.models.UserRequest;
import com.pragmatest.models.UserResponse;
import com.pragmatest.services.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/users")
    List<UserResponse> findAll() {

        List<User> users = userService.getAllUsers();

        Type responseType = new TypeToken<List<UserResponse>>() {
        }.getType();

        List<UserResponse> response = modelMapper.map(users, responseType);

        return response;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse newUser(@Valid @RequestBody UserRequest userRequest) {

        User user = modelMapper.map(userRequest, User.class);

        user = userService.saveUser(user).orElseThrow(() -> new UserInvalidException());

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return userResponse;
    }
}
