package com.payless.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Product;
import com.payless.demo.repositories.ProductRepository;



@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	
	@Override
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	
	
	
}
