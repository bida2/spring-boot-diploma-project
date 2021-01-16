package com.springboot.studentservices.repositories;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.springboot.studentservices.entities.StockGroup;

public interface StockGroupRepository extends JpaRepository<StockGroup,Long> {
	@Query(value="delete from stocks_all_groups where stock_id=?1",nativeQuery=true) @Modifying @Transactional
	void removeStockById(Long id);
	StockGroup findByStockGroupName(String stockGroupName);
	boolean existsByStockGroupName(String stockGroupName);
}
