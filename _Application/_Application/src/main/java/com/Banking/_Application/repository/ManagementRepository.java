package com.Banking._Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Banking._Application.model.Management;

@Repository
public interface ManagementRepository extends JpaRepository<Management, Integer> {

	Management findByEmail(String email);
}
