package com.payless.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Address;

@Repository
@Transactional
public interface AddressRepository extends CrudRepository<Address, Long>{

	
	
}
