package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.City;

@Repository
@Transactional
public interface CityRepository extends CrudRepository<City, Long> {
	
	List<City> findAll();
	boolean existsById(long arg0);
	Optional<City> findById(long arg0);
	<S extends City> S save(S arg0);
}
