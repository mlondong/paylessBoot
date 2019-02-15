package com.payless.demo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Purchase;






@Repository
@Transactional
public interface ConsumerRepository  extends BaseUserRepository<Consumer>{

	/*CONSULTAS PROPIEDADES*/
	Consumer findByDni(long dni);
	List<Consumer> findByFirstName(String firstName);


	/*CONSULTAS LIKE*/
	List<Consumer> findByLastNameIsLike(String lastName);
	List<Consumer> findByFirstNameLike(String lastName);


	/*OTRAS CONSULTAS DE OBJETOS*/
	List<Consumer> findByPurchase(Purchase p);

	
}
