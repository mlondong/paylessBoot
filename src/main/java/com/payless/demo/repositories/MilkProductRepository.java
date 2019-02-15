package com.payless.demo.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.MilkProduct;


@Repository
@Transactional
public interface MilkProductRepository extends ProductBaseRepository<MilkProduct>{

}
