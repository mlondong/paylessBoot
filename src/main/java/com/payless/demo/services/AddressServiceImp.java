package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Address;
import com.payless.demo.repositories.AddressRepository;

@Service
public class AddressServiceImp implements AddressService {

	
	
	@Autowired
	private AddressRepository addressRepository;
	

	
	
	@Override
	public Iterable<Address> findAll() {
		// TODO Auto-generated method stub
		return addressRepository.findAll();
	}

	@Override
	public boolean existsById(Long arg0) {
		return addressRepository.existsById(arg0);
	}

	@Override
	public Optional<Address> findById(Long arg0) {
		return addressRepository.findById(arg0);
	}

	@Override
	public void delete(Address address) {
		addressRepository.delete(address);
	}

	
	
}
