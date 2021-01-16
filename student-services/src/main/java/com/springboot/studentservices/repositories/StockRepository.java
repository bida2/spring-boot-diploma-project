package com.springboot.studentservices.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.studentservices.entities.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
	@Query(value="delete from companies_stock where stock_id=?1",nativeQuery=true) @Modifying @Transactional
	void removeById(Long sId);
	List<Stock> findByStockNameContaining(String stockName);
	Stock findByStockName(String stockName);
}
