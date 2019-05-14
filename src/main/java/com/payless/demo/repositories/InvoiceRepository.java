package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.Stock;



@Repository
@Transactional
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

	<S extends Invoice> S save(S entity);

	<S extends Invoice> Iterable<S> saveAll(Iterable<S> entities);

	Optional<Invoice> findById(Long id);
	
	Optional<Invoice> findByNumInvoice(long numInvoice);
	
	boolean existsById(Long id);

	Iterable<Invoice> findAll();

	Iterable<Invoice> findAllById(Iterable<Long> ids);

	long count();

	void deleteById(Long id);

	void delete(Invoice entity);

	void deleteAll();
	
	
	/*Queries*/
	
	@Query(value = " select * from dbpayless.invoice i "+
				   " inner join dbpayless.invoice_product ip  on i.invoice_id=ip.invoice_invoice_id "+
				   " inner join dbpayless.product p on p.product_id= ip.poduct_product_id  where i.num_invoice= :numInvoice",  nativeQuery = true)
	Optional<Invoice> findInvoiceDetails(@Param("numInvoice") Long numInvoice);
	

}
