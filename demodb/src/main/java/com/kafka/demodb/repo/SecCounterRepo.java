package com.kafka.demodb.repo;

import com.kafka.demodb.model.entity.SecurityCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecCounterRepo extends JpaRepository<SecurityCounter, Long> {

    SecurityCounter findByUserKey(String userKey);

}
