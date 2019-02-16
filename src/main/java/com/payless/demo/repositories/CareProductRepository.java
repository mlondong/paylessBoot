package com.payless.demo.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.CareProduct;



@Repository
@Transactional
public interface CareProductRepository extends ProductRepository {

}
