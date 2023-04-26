package com.kafka.demodb.repo;

import com.kafka.demodb.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccRepo extends JpaRepository<UserAccount, Long> {

    @Query(value = "SELECT * FROM user_account WHERE user_account.username = :username",
            nativeQuery = true)
    Optional<UserAccount> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM user_account WHERE user_account.email = :email",
            nativeQuery = true)
    Optional<UserAccount> findByEmail(@Param("email") String email);
}
