package com.kafka.demo.repo;

import com.kafka.demo.model.entity.BankAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccRepo extends JpaRepository<BankAccount,Long> {

    BankAccount findByUserKey(String userKey);
    Optional<BankAccount> findByAccNumber(int accNumber);

    @Query(value = "SELECT * FROM bank_account WHERE bank_account.nome = :nome",nativeQuery = true)
    List<BankAccount> findAllByName(@Param("nome") String nome);

    @Query(value = "SELECT * FROM bank_account WHERE bank_account.email = :email",
            nativeQuery = true)
    Optional<BankAccount> findByEmail(@Param("email") String email);

    @Query(value = "UPDATE bank_account SET bank_account.amountAviable = CASE " +
            "WHEN userKey1 = :userKey1 THEN :saldoattuale1 " +
            "WHEN userKey2 = :userKey2 THEN :saldoattuale2 " +
            "ELSE bank_account.amountAviable END",
            nativeQuery = true)
    @Modifying
    @Transactional
    int paymentAccount(@Param("userKey1") String userPay, @Param("saldoattuale1") double amountToPay,
                       @Param("userKey2") String userReceive, @Param("saldoattuale2") double amountToReceive);
}
