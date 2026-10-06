package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void save_shouldSaveUser() {

        User user = new User("sadik", "sadik@test.com", "1234");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals("sadik", result.getUsername());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findById_shouldReturnUser() {

        User user = new User("sadik", "sadik@test.com", "1234");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("sadik", result.get().getUsername());
    }

    @Test
    void findByUsername_shouldReturnUser() {

        User user = new User("sadik", "sadik@test.com", "1234");

        when(userRepository.findByUsername("sadik"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("sadik");

        assertTrue(result.isPresent());
        assertEquals("sadik@test.com", result.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnUser() {

        User user = new User("sadik", "sadik@test.com", "1234");

        when(userRepository.findByEmail("sadik@test.com"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("sadik@test.com");

        assertTrue(result.isPresent());
        assertEquals("sadik", result.get().getUsername());
    }
}