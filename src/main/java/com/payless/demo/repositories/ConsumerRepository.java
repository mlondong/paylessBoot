package com.payless.demo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Consumer;



@Repository
@Transactional
public interface ConsumerRepository  extends BaseUserRepository<Consumer>{

	/*CONSULTAS PROPIEDADES*/
	Consumer findByDni(long dni);
	

	/*CONSULTAS LIKE*/
	List<Consumer> findByLastNameIsLike(String lastName);
	List<Consumer> findByFirstNameLike(String lastName);
	List<Consumer> findByDniIsLike(long dni);
	

	
	/*Queries*/
	@Query("Select c from Consumer c where c.firstName like %:firstName%")
	List<Consumer> queryByFirstName(@Param("firstName") String firstName);
	
	
	/*Queries*/
	@Query("Select c from Consumer c where str(c.dni) like :dni% ")
	List<Consumer> queryByDni(@Param("dni") long dni);
	
}
