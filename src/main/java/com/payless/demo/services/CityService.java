package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import com.payless.demo.model.City;

public interface CityService {
	List<City> findAll();
	boolean existsById(Long arg0);
	Optional<City> findById(Long arg0);
	<S extends City> S save(S arg0);
}
