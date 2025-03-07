package com.pragmatest.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.pragmatest.models.User;
import com.pragmatest.models.UserEntity;
import com.pragmatest.repositories.UserRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    UserRepository userMockRepository;

    @Autowired
    UserService userService;

    @Test
    void testGetAllUsersValidUsers() {
        // Arrange
        List<UserEntity> usersList = List.of(
                new UserEntity("John Smith", "London", 45),
                new UserEntity("Mary Jones", "Manchester", 60));

        when(userMockRepository.findAll()).thenReturn(usersList);

        User expectedUser1 = new User("John Smith", "London", 45);
        User expectedUser2 = new User("Mary Jones", "Manchester", 60);

        // Act
        List<User> actualUsersList = userService.getAllUsers();

        // Assert
        assertThat(actualUsersList)
                .extracting(User::getFullName, User::getLocality, User::getAge)
                .containsExactly(
                        tuple(expectedUser1.getFullName(), expectedUser1.getLocality(), expectedUser1.getAge()),
                        tuple(expectedUser2.getFullName(), expectedUser2.getLocality(), expectedUser2.getAge()));

        verify(userMockRepository, times(1)).findAll();
    }

}
