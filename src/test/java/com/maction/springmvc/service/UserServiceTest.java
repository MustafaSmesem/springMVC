package com.maction.springmvc.service;

import com.maction.springmvc.exception.InvalidUserEmailException;
import com.maction.springmvc.model.User;
import com.maction.springmvc.repository.UserRepository;
import com.maction.springmvc.utils.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService underTest;
    private AutoCloseable closable;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    void getUserById() {

        var ID = 2543L;
        var expected = Optional.of(new User(ID));
        Mockito.when(userRepository.getUserById(ID)).thenReturn(expected);

        var result = underTest.getUserById(ID);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        System.out.println(argumentCaptor.getValue());
        assertEquals(expected.get(), result);

    }


    @Test
    void getUserByIdShouldThrowEntityNotFoundException() {

        var ID = 2543L;
        Mockito.when(userRepository.getUserById(ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
           underTest.getUserById(ID);
        });

        String expectedMessage = "User " + ID + " is not found";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

    }
}
