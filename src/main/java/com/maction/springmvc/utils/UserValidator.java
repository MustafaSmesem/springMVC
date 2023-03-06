package com.maction.springmvc.utils;

import com.maction.springmvc.exception.InvalidPasswordException;
import com.maction.springmvc.exception.InvalidUserEmailException;
import com.maction.springmvc.exception.InvalidUsernameException;
import com.maction.springmvc.model.User;
import com.maction.springmvc.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    public static void validateUserName(String name) {
        if (name == null || name.length() < 5 || name.length() > 32)
            throw new InvalidUsernameException("username length should be between 5 and 32 char");
        if (name.toLowerCase().contains("test"))
            throw new InvalidUsernameException("username cannot contain 'test'");
    }

    public static void validateUserPassword(String password, String name) {
        if (password == null || password.length() < 8 || password.length() > 36)
            throw new InvalidPasswordException("password length should be between 8 and 36 char");
        if (password.toLowerCase().contains("test") || password.toLowerCase().contains("password"))
            throw new InvalidPasswordException("password cannot contain 'test' or 'password' word" );
        if (password.toLowerCase().contains(name))
            throw new InvalidPasswordException("password cannot contain user name");
        boolean hasDigit = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasSpecial = false;
        for (var ch:password.toCharArray()) {
            if(Character.isDigit(ch))
                hasDigit = true;
            else if (Character.isUpperCase(ch))
                hasUpperCase = true;
            else if (Character.isLowerCase(ch))
                hasLowerCase = true;
            else if (!Character.isLetterOrDigit(ch) && !Character.isWhitespace(ch))
                hasSpecial = true;
        }
        if (!hasDigit)
            throw new InvalidPasswordException("password should contain at least one digit");
        if (!hasUpperCase || !hasLowerCase)
            throw new InvalidPasswordException("password should contain at least one upper case and one letter case character");
        if (!hasSpecial)
            throw new InvalidPasswordException("password should contain at least one special character");
    }

    public static void validateUserEmail(String email, UserService userService) {
        if (email == null || email.isEmpty()) throw new InvalidUserEmailException("Email cannot be empty");
        var r = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!r.matcher(email).matches()) throw new InvalidUserEmailException("Email is not valid");
        var optUser = userService.findUserByEmail(email);
        if (optUser.isPresent()) throw new InvalidUserEmailException("This email is already exist");
    }

    public static void validate(User user, UserService userService) {
        validateUserName(user.getName());
        validateUserEmail(user.getEmail(), userService);
        validateUserPassword(user.getPassword(), user.getName());
    }
}
