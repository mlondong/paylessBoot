package com.payless.demo.repositories;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Indexed;

import com.payless.demo.model.Trader;
import com.payless.demo.model.Usser;



/*
 * @NoRepositoryBean : no permite que se cree una instancia de bean para este, permitiendo que las interfaces que extiendan de 
 * este solo implementen los metodos que este declara, se extiende de Usser dado que es un inheritance
 * T es la Entidad
 * */

@NoRepositoryBean
public interface BaseUserRepository<T extends Usser> extends Repository<T, Long> {

	<S extends T> S save(S entity);

//	<S extends T> S save(Iterable<S> entities); tiene problemas sale uneror no sabmos porq
	
	Optional<T> findById(Long id);
	
	Iterable<T> findAll();

	Iterable<T> findAllById(Iterable<Long> ids);

	Iterable<T> findAllById(Pageable sort);
	
	
	
	boolean existsById(Long id);
	
	long count();

	void deleteById(Long id);

	void delete(T entity);


		
}
