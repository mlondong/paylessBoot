package com.payless.demo.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.MilkProduct;
import com.payless.demo.model.Product;

@Repository
@Transactional
public interface ProductRepository extends BaseProductRepository<Product>{

}
