package com.kafka.demodb.repo;

import com.kafka.demodb.model.entity.UserFinalncial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFinancialRepo extends JpaRepository<UserFinalncial, Long> {

    UserFinalncial findByUserKey(String userKey);
    UserFinalncial findByEmail(String email);
    UserFinalncial findByUsername(String username);
}
