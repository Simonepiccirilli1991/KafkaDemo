package com.kafka.demodb.repo;

import com.kafka.demodb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByUserKey(String username);
    Optional<User> findUserByEmail(String username);

}
