package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.City;
import com.payless.demo.repositories.CityRepository;

@Service
public class CityServiceImp implements CityService {

	@Autowired
	private CityRepository cityrepository;

	@Override
	public List<City> findAll() {
		return cityrepository.findAll();
	}

	@Override
	public boolean existsById(Long arg0) {
		return cityrepository.existsById(arg0);
	}

	@Override
	public Optional<City> findById(Long arg0) {
		return cityrepository.findById(arg0);
	}

	@Override
	public <S extends City> S save(S arg0) {
		return cityrepository.save(arg0);
	}

	
	
	
}
