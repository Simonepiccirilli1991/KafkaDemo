package com.kafka.demodb.repo;

import com.kafka.demodb.model.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecRepo extends JpaRepository<UserSecurity, Long> {

    UserSecurity findByUserKey(String userKey);
}
