package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.payless.demo.model.Product;

@Repository
@Transactional
public interface ProductRepository extends BaseProductRepository<Product>{

//	@Query("Select c from Consumer c where c.firstName like %:firstName%")

	@Query(value = "select p from Product p where p.description like %:description% ")
	List<Product>findByContainDescription(@Param("description") String description);
		
}
