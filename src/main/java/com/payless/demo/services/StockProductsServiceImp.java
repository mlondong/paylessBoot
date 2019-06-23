package com.payless.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.StockProducts;
import com.payless.demo.repositories.StockProductsRepository;

@Service
public class StockProductsServiceImp implements StockProductsService{

	@Autowired
	private StockProductsRepository stockProductsRepository;
	
	@Override
	public List<StockProducts> findProductsInTraders(List<Long> idProducts, List<Long> idTraders) {
		return stockProductsRepository.findProductsInTraders(idProducts, idTraders);
	}

	@Override
	public List<StockProducts> findProductsInStock(Long idStock) {
		return stockProductsRepository.findProductsInStock(idStock);
	}

	@Override
	public Iterable<StockProducts> findAll() {
		return stockProductsRepository.findAll();
	}

	
	
	
}
