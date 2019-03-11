package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;

public interface ConsumerService {

	Consumer save(Consumer entity);
	Consumer findByDni(long dni);
	
	Optional<Consumer> findById(Long id);
	
	List<Consumer> findByLastNameIsLike(String lastName);
	List<Consumer> findByFirstNameLike(String lastName);
	List<Consumer> queryByFirstName(String firstName);
	
	Iterable<Consumer> findAll();
	Iterable<Consumer> findAllById(Iterable<Long> ids);
	
	
	boolean existsById(Long id);
	long count();

	void deleteById(Long id);
	void delete(Consumer entity);
	void deleteAll();
	
}
