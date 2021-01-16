package com.springboot.studentservices.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.stereotype.Repository;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.springboot.studentservices.entities.Stock;

@Repository
@Transactional
public class StockSearchRepository {
	
	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  public List<Stock> searchByStockName(String text) throws InterruptedException {
		  
		  	
		    // get the full text entity manager
		    FullTextEntityManager fullTextEntityManager =
		      org.hibernate.search.jpa.Search.
		      getFullTextEntityManager(entityManager);
		    // Test line - purge and optimize on start - used for reindexing
		    // If NoSuchFileException occurs, MANUALLY delete all the directories related to indexing
		    // Examples are com.springboot.studentservices.entities.Stock and com.springboot.studentservices.entities.Companies
		    fullTextEntityManager.createIndexer(Stock.class).purgeAllOnStart(true).optimizeAfterPurge(true).optimizeOnFinish(true).start();
		    QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Stock.class)
		    	    // Here come the assignments of "query" analyzers
		    	    .overridesForField( "stockName", "customanalyzer_query" )
		    	    .get();
		    // a very basic query by keywords
		    org.apache.lucene.search.Query query =
		      queryBuilder
		        .keyword()
		        .onFields("stockName")
		        .matching(text)
		        .createQuery();
		    
		    org.hibernate.search.jpa.FullTextQuery jpaQuery =
		      fullTextEntityManager.createFullTextQuery(query, Stock.class);
		  
		    // execute search and return results (sorted by relevance as default)
		    @SuppressWarnings("unchecked")
		    List<Stock> results = jpaQuery.getResultList();
		    
		    return results;
		  } // method search

}
