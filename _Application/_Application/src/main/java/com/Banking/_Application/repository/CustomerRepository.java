package com.Banking._Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Banking._Application.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
