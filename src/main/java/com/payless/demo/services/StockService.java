package com.payless.demo.services;

import java.util.Optional;

import com.payless.demo.model.Stock;

public interface StockService {

	Stock save(Stock stock);

	Iterable<Stock> saveAll(Iterable<Stock> stocks);

	Optional<Stock> findById(Long id);

	boolean existsById(Long id);

	Iterable<Stock> findAll();

	Iterable<Stock> findAllById(Iterable<Long> ids);

	long count();

	void deleteById(Long id);

	void delete(Stock entity);
	
	void deleteAll();


}
