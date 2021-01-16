package com.springboot.studentservices.entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.Parameter;


@AnalyzerDef(name = "customanalyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { @Parameter(name = "language", value = "English") }),
        @TokenFilterDef(factory = NGramFilterFactory.class, params = { @Parameter(name = "maxGramSize", value = "15") })

})
@AnalyzerDef(name = "customanalyzer_query", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { @Parameter(name = "language", value = "English") })

})
@Entity
@Indexed
@Table(name="Stock")
public class Stock {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;
	@NotNull @Size(min=1,max=60) 
	@Field(termVector = TermVector.YES,analyze = Analyze.YES,analyzer = @Analyzer(definition = "customanalyzer"))
	@Column(name = "stock_name",nullable=false)
	private String stockName;
	@NotNull @Size(min=1,max=1000) 
	@Column(name = "stock_description", nullable = false)
	private String stockDesc;
	@NotNull
	@Column(name = "measuring_units",nullable = false)
	private String measuringUnits;
	@NotNull @DecimalMin(value="0.1",inclusive=true) 
	@Column(name="weight_one",nullable = false) 
	private float weightOne;
	@NotNull @Min(1)
	@Column(name="quantity",nullable = false) 
	private int quantity;
	@NotNull @DecimalMin(value="0.1",inclusive=true)
	@Column(name="price_per_item",nullable=false)
	private float pricePerItem;
	@Column(name="thumb_path",nullable=false) 
	private String thumbnail;
	@Column(name="reserve_date",nullable=true)
	private LocalDateTime reserveDate;
	@Column(name="final_price",nullable=false)
	private float finalPrice;
	// Encoded and Base64 fields go here --> needed for ePay payment
	// DO NOT create them with @Column
	private String sha1HMAC;
	private String base64Encoded;
	
	
	public Stock() {
		super();
	}

	public Stock(String stockName, String stockDesc,String measuringUnits,float weightOne,int quantity,float pricePerItem,String thumbnail) {
		super();
		this.stockName = stockName;
		this.stockDesc = stockDesc;
		this.measuringUnits = measuringUnits;
		this.weightOne = weightOne;
		this.pricePerItem = pricePerItem;
		this.thumbnail = thumbnail;
		this.quantity = quantity;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "stocks_all_groups",
            joinColumns = { @JoinColumn(name = "stock_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") })
	private List<StockGroup> groups = new ArrayList<StockGroup>();
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "stocks")
    private Set<Companies> companies = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "resStocks")
    private Set<Companies> resCompanies = new HashSet<>();
	
	public Set<Companies> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Companies> companies) {
		this.companies = companies;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getStockDesc() {
		return stockDesc;
	}
	public void setStockDesc(String stockDesc) {
		this.stockDesc = stockDesc;
	}
	public String getMeasuringUnits() {
		return measuringUnits;
	}
	public void setMeasuringUnits(String measuringUnits) {
		this.measuringUnits = measuringUnits;
	}
	public float getPricePerItem() {
		return pricePerItem;
	}
	public void setPricePerItem(float pricePerItem) {
		this.pricePerItem = pricePerItem;
	}
	
	@Override
	public String toString() {
		return String.format("Company: [id=%s, Stock Name=%s, Stock Description=%s,Measuring Units=%s,Total Weight=%s,Price Per Item=%s]", id,stockDesc, stockName, measuringUnits,weightOne,pricePerItem);
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public float getWeightOne() {
		return weightOne;
	}

	public void setWeightOne(float weightOne) {
		this.weightOne = weightOne;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<StockGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<StockGroup> groups) {
		this.groups = groups;
	}

	public LocalDateTime getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(LocalDateTime reserveDate) {
		this.reserveDate = reserveDate;
	}

	public Set<Companies> getResCompanies() {
		return resCompanies;
	}

	public void setResCompanies(Set<Companies> resCompanies) {
		this.resCompanies = resCompanies;
	}

	public float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getSha1HMAC() {
		return sha1HMAC;
	}

	public void setSha1HMAC(String sha1hmac) {
		sha1HMAC = sha1hmac;
	}

	public String getBase64Encoded() {
		return base64Encoded;
	}

	public void setBase64Encoded(String base64Encoded) {
		this.base64Encoded = base64Encoded;
	}
	
}
