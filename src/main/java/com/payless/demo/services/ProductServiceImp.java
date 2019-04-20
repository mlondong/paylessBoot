package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Product;
import com.payless.demo.repositories.ProductRepository;



@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	
	@Override
	public List<Product>findByContainDescription(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByContainDescription(name);
	}



	@Override
	public Optional<Product> findById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}



	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.existsById(id);
	}

	
	
	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	
	
	
}
