package com.springboot.studentservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.studentservices.entities.Companies;
import com.springboot.studentservices.entities.EpayCredentials;

@Repository
public interface EpayCredentialsRepository extends JpaRepository<EpayCredentials,Long> {
	EpayCredentials findByMerchantCompanyId(Companies merchCompany);
	EpayCredentials findByMerchantCompanyId(Long merchantCompanyId);
}
