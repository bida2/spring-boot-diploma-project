package com.springboot.studentservices.entities;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;




@Entity
@Table(name="stockGroup")
public class StockGroup {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;
	@NotNull @Size(min=1,max=60) @Pattern(regexp="^[a-zA-Z\\& ]+$")
	@Column(name = "stock_group_name",nullable=false)
	private String stockGroupName;
	
	public StockGroup() {}
	
	public StockGroup(String stockGroupName) {
		this.stockGroupName = stockGroupName;
	}
	
	@ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "groups")
	private Set<Stock> stockGroups = new HashSet<Stock>();
	
	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStockGroupName() {
		return stockGroupName;
	}
	public void setStockGroupName(String stockGroupName) {
		this.stockGroupName = stockGroupName;
	}

	public Set<Stock> getStockGroups() {
		return stockGroups;
	}

	public void setStockGroups(Set<Stock> stockGroups) {
		this.stockGroups = stockGroups;
	}

	

	
}
