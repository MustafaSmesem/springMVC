package com.maction.springmvc.controller;

import com.maction.springmvc.exception.InvalidUserEmailException;
import com.maction.springmvc.exception.InvalidUsernameException;
import com.maction.springmvc.model.Role;
import com.maction.springmvc.model.User;
import com.maction.springmvc.model.rest.ErrorResponse;
import com.maction.springmvc.model.rest.Response;
import com.maction.springmvc.service.UserService;
import com.maction.springmvc.utils.UserCreateDateComparator;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.maction.springmvc.utils.UserValidator.validate;
import static com.maction.springmvc.utils.UserValidator.validateUserName;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User save(@RequestBody User user) {
        validate(user, userService);
        var newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        newUser.setActive(true);
        var roles = new ArrayList<Role>();
        roles.add(new Role("STANDARD_USER"));
        if (user.isAdmin()) {
            roles.add(new Role("ADMIN_USER"));
            newUser.setAdmin(true);
        }
        newUser.setRoles(roles);
        return userService.save(newUser);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getUsers(@Param("p") Integer pageNumber, @Param("s") Integer pageSize) {
        return userService.getAllUsers(pageNumber, pageSize);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        var response = new Response();
        response.setStatus(200);
        response.setMessage("User " + id + " is deleted successfully.");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        var oldUser = userService.getUserById(newUser.getId());
        validateUserName(newUser.getName());
        newUser.setUpdated(LocalDateTime.now());
        newUser.setEmail(oldUser.getEmail());
        return userService.save(newUser);
    }


    @ExceptionHandler({InvalidUsernameException.class, InvalidUserEmailException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        var response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(400);
        return ResponseEntity.badRequest().body(response);
    }
}
