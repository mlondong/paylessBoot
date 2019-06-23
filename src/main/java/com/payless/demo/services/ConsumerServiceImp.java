package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Consumer;
import com.payless.demo.repositories.ConsumerRepository;

@Service
public class ConsumerServiceImp implements ConsumerService{

	@Autowired
	private ConsumerRepository consumerRepository;

	
	
	
	@Override
	public Consumer findByNameIslike(long dni) {
		return null;
	}

	@Override
	public Consumer queryFindByUserName(String name) {
		return consumerRepository.queryFindByUserName(name);
	}

	@Override
	public List<Consumer> queryByDni(long dni) {
		return consumerRepository.queryByDni(dni);
	}

	@Override
	public List<Consumer> findByDniIsLike(long dni) {
		return consumerRepository.findByDniIsLike(dni);
	}

	@Override
	public Consumer findByDni(long dni) {
		return consumerRepository.findByDni(dni);
	}

	@Override
	public Consumer save(Consumer entity) {
		return consumerRepository.save(entity);
	}

	
	@Override
	public List<Consumer> findByLastNameIsLike(String lastName) {
		return consumerRepository.findByLastNameIsLike(lastName);
	}

	@Override
	public List<Consumer> findByFirstNameLike(String lastName) {
		return consumerRepository.findByFirstNameLike(lastName);
	}


	@Override
	public List<Consumer> queryByFirstName(String firstName) {
		return consumerRepository.queryByFirstName(firstName);
	}


	@Override
	public Optional<Consumer> findById(Long id) {
		return consumerRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return consumerRepository.existsById(id);
	}

	@Override
	public Iterable<Consumer> findAll() {
		return consumerRepository.findAll();
	}

	@Override
	public Iterable<Consumer> findAllById(Iterable<Long> ids) {
		return consumerRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return consumerRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		consumerRepository.deleteById(id);
	}

	@Override
	public void delete(Consumer entity) {
		consumerRepository.delete(entity);		
	}

	@Override
	public void deleteAll() {}

	
	

}
