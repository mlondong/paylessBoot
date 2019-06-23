package com.payless.demo.services;

import java.util.Optional;

import com.payless.demo.model.Address;


public interface AddressService {
	Iterable<Address> findAll();
	boolean existsById(Long arg0);
	Optional<Address> findById(Long arg0);
	void delete(Address address);

}
