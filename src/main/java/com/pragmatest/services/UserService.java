package com.pragmatest.services;

import java.util.List;
import java.util.Optional;

import com.pragmatest.models.User;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> saveUser(User user);
}
