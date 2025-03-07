package com.pragmatest.controllers;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.pragmatest.models.User;
import com.pragmatest.models.UserResponse;
import com.pragmatest.services.UserService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerUnitTest {

        @Autowired
        UserController userController;

        @MockBean
        UserService mockUserService;

        @Test
        public void testGetUsersValidUsers() {
                // Arrange
                List<User> users = Arrays.asList(
                                new User(1L, "John Smith", "London", 23),
                                new User(2L, "Mary Walsh", "Liverpool", 30));

                when(mockUserService.getAllUsers()).thenReturn(users);

                UserResponse expectedUserResponse1 = new UserResponse(1L, "John Smith", "London", 23);
                UserResponse expectedUserResponse2 = new UserResponse(2L, "Mary Walsh", "Liverpool", 30);

                // Act
                List<UserResponse> actualResponse = userController.findAll();

                // Assert
                assertThat(actualResponse)
                                .extracting(UserResponse::getId, UserResponse::getFullName,
                                                UserResponse::getLocality, UserResponse::getAge)
                                .containsExactly(
                                                tuple(expectedUserResponse1.getId(),
                                                                expectedUserResponse1.getFullName(),
                                                                expectedUserResponse1.getLocality(),
                                                                expectedUserResponse1.getAge()),
                                                tuple(expectedUserResponse2.getId(),
                                                                expectedUserResponse2.getFullName(),
                                                                expectedUserResponse2.getLocality(),
                                                                expectedUserResponse2.getAge()));

                verify(mockUserService, times(1)).getAllUsers();
        }

}
