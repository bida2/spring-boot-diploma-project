package com.springboot.studentservices.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;





@Entity
@Table(name = "Companies")
public class Companies {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;
	@NotNull @Size(min=2,max=60) @Pattern(regexp="^[a-zA-Z0-9\\u0400-\\u04FF]+(([',. -][a-zA-Z\\u0400-\\u04FF ])?[a-zA-Z0-9\\u0400-\\u04FF]*)*$")
	@Column(name = "company_name",nullable=false,unique=true)
	private String companyName;
	@NotNull @Size(min=9,max=11) @Pattern(regexp="^([A-Z\\u0400-\\u04FF]{2,})?[0-9]{9,9}$")
	@Column(name = "bulstat", nullable = false)
	private String bulstat;
	@NotNull @Size(max=100) @Pattern(regexp="^[a-zA-Z\\u0400-\\u04FF]+\\,+[a-zA-Z\\u0400-\\u04FF]+\\,+[a-zA-Z\\u0400-\\u04FF]+\\,+[a-zA-Z0-9\\u0400-\\u04FF ]{2,}$")
	@Column(name = "location",nullable = false)
	private String location;
	@NotNull @Size(min=10,max=13) @Pattern(regexp="^(\\+[0-9]{0,3})?([0-9]{10})$")
	@Column(name="phone_number",nullable = false) 
	private String phoneNumber;
	@NotNull @Size(max=60) @Pattern(regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	@Column(name="email",nullable=false,unique=true)
	private String email;
	@NotNull @Size(min=8) 
	@Column(name="password",nullable=false)
	private String password;
	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique=true)
	private User user;
	
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "companies_stock",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "stock_id") })
    private Set<Stock> stocks = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "res_stocks_by_company",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "stock_id") })
    private Set<Stock> resStocks = new HashSet<>();



	public Companies() {
		super();
	}

	public Companies(String companyName, String bulstat,String location,String phoneNumber,String email,String password) {
		super();
		this.companyName = companyName;
		this.bulstat = bulstat;
		this.location = location;
		this.phoneNumber = phoneNumber;
		this.email=email;
		this.password=password;
	}

	public Set<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(Set<Stock> stocks) {
		this.stocks = stocks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBulstat() {
		return bulstat;
	}

	public void setBulstat(String bulstat) {
		this.bulstat = bulstat;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation (String location) {
		this.location = location;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("Company: [id=%s, Company Name=%s, Bulstat=%s,Location=%s,Phone Number=%s,Email=%s]", id, companyName, bulstat,location,phoneNumber,email);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Stock> getResStocks() {
		return resStocks;
	}

	public void setResStocks(Set<Stock> resStocks) {
		this.resStocks = resStocks;
	}

}
