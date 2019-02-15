package com.payless.demo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.MeatProduct;


@Repository
@Transactional
public interface MeatProductRepository extends ProductBaseRepository<MeatProduct>  {

	List<MeatProduct> findByTypeAnimal(String animal);
	
}
