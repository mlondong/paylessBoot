package com.payless.demo.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;

@Repository
@Transactional
public interface InvoiceProductRepository extends CrudRepository<InvoiceProduct, Long> {

	
}
