package com.kafka.demo.repo;

import com.kafka.demo.model.entity.BankAccSic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccSicRepo extends JpaRepository<BankAccSic,Long> {

    BankAccSic findByUserKey(String userKey);
    Optional<BankAccSic> findByAccNumber(int accNumber);
}
