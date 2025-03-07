package com.pragmatest.services;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragmatest.models.User;
import com.pragmatest.models.UserEntity;
import com.pragmatest.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        Type returnType = new TypeToken<List<User>>() {
        }.getType();

        List<User> users = modelMapper.map(userEntities, returnType);

        return users;
    }

    @Override
    public Optional<User> saveUser(User user) {
        Optional<UserEntity> savedUserEntity = Optional.empty();

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        savedUserEntity = Optional.of(userRepository.save(userEntity));

        if (savedUserEntity.isEmpty()) {
            return Optional.empty();
        }

        userEntity = savedUserEntity.get();

        User savedUser = modelMapper.map(userEntity, User.class);

        return Optional.of(savedUser);
    }

}
