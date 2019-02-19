package com.payless.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Purchase;

@Repository
@Transactional
public interface PurchaseRepository  extends CrudRepository<Purchase, Long>{

	
}
