package com.springboot.studentservices.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="epay_credentials")
public class EpayCredentials {
	@Id
	@GeneratedValue
	@Column(name="credentials_id")	
	private Long id;
	@Column(name="merchant_id")
	private String merchantId;
	@Column(name="merchant_secret")
	private String merchantSecret;
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="merch_comp_id",referencedColumnName="id",unique=true)
	private Companies merchantCompany;
	
	// Getters and Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantSecret() {
		return merchantSecret;
	}
	public void setMerchantSecret(String merchantSecret) {
		this.merchantSecret = merchantSecret;
	}
	public Companies getMerchantCompany() {
		return merchantCompany;
	}
	public void setMerchantCompany(Companies merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	
	
}
