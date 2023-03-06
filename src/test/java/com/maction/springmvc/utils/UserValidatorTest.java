package com.maction.springmvc.utils;

import com.maction.springmvc.exception.InvalidUserEmailException;
import com.maction.springmvc.model.User;
import com.maction.springmvc.repository.UserRepository;
import com.maction.springmvc.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class UserValidatorTest {

    @AfterEach
    void tearDown() {
        System.out.println("test finished");
    }

    @BeforeEach
    void setUp() {
        System.out.println("test will start");
    }

    @Test
    void itShouldValidateEmailWithoutException() {
        String email = "mustafa@gmail.com";

        var userService = Mockito.mock(UserService.class);

        UserValidator.validateUserEmail(email, userService);
    }

    @Test
    void itShouldValidateEmailWithInvalidEmailException() {
        String email = "mustafa@gmai";

        var userService = Mockito.mock(UserService.class);

        Exception exception = assertThrows(InvalidUserEmailException.class, () -> {
            UserValidator.validateUserEmail(email, userService);
        });

        String expectedMessage = "Email is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void itShouldValidateEmailWithEmailAlreadyExistException() {
        String email = "mustafa@gmail.com";
        var userOpt = Optional.of(new User());

        var userService = Mockito.mock(UserService.class);
        Mockito.when(userService.findUserByEmail(email)).thenReturn(userOpt);

        Exception exception = assertThrows(InvalidUserEmailException.class, () -> {
            UserValidator.validateUserEmail(email, userService);
        });

        String expectedMessage = "This email is already exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
