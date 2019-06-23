package com.payless.demo.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.payless.demo.model.InvoiceProduct;

@Repository
@Transactional
public interface InvoiceProductRepository extends CrudRepository<InvoiceProduct, Long> {

}
