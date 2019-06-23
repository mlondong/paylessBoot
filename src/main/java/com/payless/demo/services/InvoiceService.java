package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.payless.demo.model.Invoice;

public interface InvoiceService {

	Invoice	save(Invoice entity);

	Optional<Invoice> findById(Long id);
	
	Optional<Invoice> findByNumInvoice(long numInvoice);


	boolean existsById(Long id);

	Iterable<Invoice> findAll();

	Iterable<Invoice> findAllById(Iterable<Long> ids);

	long count();

	void deleteById(Long id);

	void delete(Invoice entity);

	void deleteAll();
	
	Optional<Invoice> findInvoiceDetails(Long numInvoice);

	
}
