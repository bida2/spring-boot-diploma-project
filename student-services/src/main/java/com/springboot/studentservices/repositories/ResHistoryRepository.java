package com.springboot.studentservices.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.studentservices.entities.ReservationHistory;

public interface ResHistoryRepository extends JpaRepository<ReservationHistory,Long> {
	List<ReservationHistory> findByCompanyName(String companyName);
}
