package com.payless.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Stock;
import com.payless.demo.repositories.StockRepository;

@Service
public class StockServiceImp implements StockService{

	
	@Autowired
	private StockRepository stockRepository;
	
	@Override
	public Stock save(Stock stock) {
		return stockRepository.save(stock);
	}

	@Override
	public Iterable<Stock> saveAll(Iterable<Stock> stocks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Stock> findById(Long id) {
		return stockRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return stockRepository.existsById(id);
	}

	@Override
	public Iterable<Stock> findAll() {
		return stockRepository.findAll();
	}

	@Override
	public Iterable<Stock> findAllById(Iterable<Long> ids) {
		return stockRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return stockRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		stockRepository.deleteById(id);	
	}

	@Override
	public void delete(Stock entity) {
		stockRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		stockRepository.deleteAll();		
	}

	
	
}
