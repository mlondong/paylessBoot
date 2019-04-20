package com.payless.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.MilkProduct;
import com.payless.demo.model.Product;

@Repository
@Transactional
public interface ProductRepository extends BaseProductRepository<Product>{

	
	
	@Query(value = "select * from dbpayless.product p where description like %:description% ", nativeQuery = true)
	List<Product>findByContainDescription(@Param("description") String description);
}
