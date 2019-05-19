package com.payless.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Rating;
import com.payless.demo.repositories.RatingRepository;

@Service
public class RatingServiceImp implements RatingService{

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Rating save(Rating entity) {
		return ratingRepository.save(entity);
	}

	@Override
	public Optional<Rating> findById(Long id) {
		return ratingRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return ratingRepository.existsById(id);
	}

	@Override
	public Iterable<Rating> findAll() {
		return ratingRepository.findAll();
	}
	
	
	
	
}
