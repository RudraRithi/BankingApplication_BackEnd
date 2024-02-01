package com.Banking._Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Banking._Application.model.BankAccount;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, Long> {

}
