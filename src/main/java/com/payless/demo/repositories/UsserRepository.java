package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Usser;


@Repository
@Transactional
public interface UsserRepository extends BaseUserRepository<Usser>{

	/*Metodos comunes a Usser y sus herederos*/
	Usser  findUsserById(long id);
	Optional<Usser> findUsserByName(String name);
	List<Usser> findUsserByState(boolean state);
	


}
