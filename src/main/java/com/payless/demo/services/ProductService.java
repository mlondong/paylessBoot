package com.payless.demo.services;

import java.util.List;

import com.payless.demo.model.Product;;

public interface ProductService {

	Product save(Product product);
	Iterable<Product> findAll();

	
}
