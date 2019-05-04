package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.payless.demo.model.Product;


@NoRepositoryBean
public interface BaseProductRepository<T extends Product> extends CrudRepository<T, Long> {


	<S extends T> S save(S entity);

	Optional<T> findById(Long id);

	boolean existsById(Long id);
	
	Iterable<T> findAll();

	Iterable<T> findAllById(Iterable<Long> ids);

	long count();

	void deleteById(Long id);

	void delete(T entity);
	
	List<T>findByContainDescription(String description);


}
