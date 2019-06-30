package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Trader;



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
	@Query(value= "select * from dbpayless.usser u"+ 
				  " inner join dbpayless.consumer c on u.user_id=c.user_id"+
				  "	where u.name like %:name% ", nativeQuery = true)
	Consumer queryFindByUserName(@Param("name") String name);
	
	
	
	/*Queries*/
	@Query("Select c from Consumer c where str(c.dni) like :dni% ")
	List<Consumer> queryByDni(@Param("dni") long dni);
	

	/*Queries*/
	@Query(value = " Select * FROM dbpayless.consumer c "
				+  " inner join dbpayless.usser u ON U.user_id = C.user_id "
				+  " where u.name like %:name% ",  nativeQuery = true)
	Optional<Consumer> findNameContainInConsumer(@Param("name")String name);

 	
	
	/*Queries*/
	@Query(value=" Select * FROM Consumer T "
				 +  "inner join usser U ON U.user_id = T.user_id "
				 +  "inner join address A on A.address_id = T.address_id "  
				 +  "where a.city_id in (?1) and a.zone_id in(?2) " , nativeQuery = true)
	List<Consumer> queryByParametersCityZone(@Param("cities") Set<Long>cities, @Param("city") Set<Long> zones);

	
}
