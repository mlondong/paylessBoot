package com.payless.demo.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.payless.demo.model.StockProducts;

public interface StockProductsService {
	List<StockProducts> findProductsInTraders(List<Long> idProducts, List<Long> idTraders);
	List<StockProducts> findProductsInStock(Long idStock);
}
