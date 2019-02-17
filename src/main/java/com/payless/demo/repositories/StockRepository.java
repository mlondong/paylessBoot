package com.payless.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Stock;

@Repository
@Transactional
public interface StockRepository extends CrudRepository<Stock, Long>{
	
	<S extends Stock> S save(S entity);

	<S extends Stock> Iterable<S> saveAll(Iterable<S> entities);

	Optional<Stock> findById(Long id);

	boolean existsById(Long id);

	Iterable<Stock> findAll();

	Iterable<Stock> findAllById(Iterable<Long> ids);

	 long count();

	void deleteById(Long id);

	void delete(Stock entity);
	
	void deleteAll();

	
	
}
