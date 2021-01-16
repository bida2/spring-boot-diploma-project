package com.springboot.studentservices.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="res_history")
public class ReservationHistory {
	@Id
	@GeneratedValue
	@Column(name="res_id")
	private Long resId;
	@Column(name="company_name")
	private String companyName;
	@Column(name="stock_id")
	private Long stockId;
	@Column(name="company_id")
	private Long companyId;
	@Column(name="res_date")
	private LocalDateTime resDate;
	@Column(name="stock_name")
	private String stockName;
	
	// Getters and Setters
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public LocalDateTime getResDate() {
		return resDate;
	}
	public void setResDate(LocalDateTime resDate) {
		this.resDate = resDate;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
}
