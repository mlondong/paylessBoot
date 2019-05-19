package com.payless.demo.services;

import java.util.Optional;

import com.payless.demo.model.Rating;


public interface RatingService {

	Rating save(Rating entity);
	Optional<Rating> findById(Long id);
	boolean existsById(Long id);
	Iterable<Rating> findAll();

	
}
