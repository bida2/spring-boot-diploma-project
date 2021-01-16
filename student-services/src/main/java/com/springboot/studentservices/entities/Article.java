package com.springboot.studentservices.entities;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="Article")
public class Article {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;
	@NotNull @Size(min=10,max=200) 
	@Column(name = "article_title",nullable=false,unique=true)
	private String articleTitle;
	@NotNull @Size(min=50,max=10000) 
	@Column(name = "article_text",nullable=false)
	private String articleText;
	@Column(name = "post_date",nullable=false)
	private LocalDate postDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.MERGE
            })
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private User poster;
	
	// Constructors
	public Article() {}
	
	public Article(String articleTitle,String articleText, LocalDate postDate,User poster) {
		this.articleTitle = articleTitle;
		this.articleText = articleText;
		this.postDate = postDate;
		this.poster = poster;
	}
	
	
	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleText() {
		return articleText;
	}
	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}
	public LocalDate getPostDate() {
		return postDate;
	}
	public void setPostDate(LocalDate postDate) {
		this.postDate = postDate;
	}

	public User getPoster() {
		return poster;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}
}
