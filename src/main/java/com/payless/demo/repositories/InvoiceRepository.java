package com.payless.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Invoice;
import com.payless.demo.model.Stock;



@Repository
@Transactional
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

	<S extends Invoice> S save(S entity);

	<S extends Invoice> Iterable<S> saveAll(Iterable<S> entities);

	Optional<Invoice> findById(Long id);

	boolean existsById(Long id);

	Iterable<Invoice> findAll();

	Iterable<Invoice> findAllById(Iterable<Long> ids);

	long count();

	void deleteById(Long id);

	void delete(Invoice entity);

	void deleteAll();
	

}
