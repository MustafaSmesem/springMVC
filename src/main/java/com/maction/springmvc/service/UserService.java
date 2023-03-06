package com.maction.springmvc.service;

import com.maction.springmvc.model.User;
import com.maction.springmvc.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        log.info("User id {}", id);
        var user = userRepository.getUserById(id);
        if (user.isEmpty())
            throw new EntityNotFoundException("User " + id + " is not found");

        return user.get();
    }

    public List<User> getAllUsers(int pageNum, int pageSize) {
        var pageRequest = PageRequest.of(pageNum, pageSize, Sort.by("updated").descending());
        return userRepository.findAllByDeletedIsFalseWithPagination(pageRequest);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deleteUserById(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
