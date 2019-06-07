package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.payless.demo.model.Product;;

public interface ProductService {

	Product save(Product product);
	Iterable<Product> findAll();
	boolean existsById(Long id);
	Optional<Product> findById(Long id);


	List<Product>findByContainDescription(String description);
	

}
