package com.payless.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payless.demo.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{
	
}
