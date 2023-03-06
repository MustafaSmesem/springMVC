package com.maction.springmvc.repository;

import com.maction.springmvc.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);
    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT u FROM User u")
    List<User> findAllByDeletedIsFalseWithPagination(Pageable pageable);
}
