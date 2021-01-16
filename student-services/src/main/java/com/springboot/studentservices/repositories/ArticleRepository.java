package com.springboot.studentservices.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.studentservices.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
	Article getById(Long id);
}
