package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Stock;
import com.payless.demo.model.Trader;




@Repository
@Transactional
public interface TraderRepository extends BaseUserRepository<Trader>{

	Trader findByCuit(long cuit);
	
	List<Trader> findByStock(Stock stock);

	Optional<Trader> findByName(String name);
	
	/*Queries*/
	@Query(value=" Select * FROM trader T "
				 +  "inner join usser U ON U.user_id = T.user_id "
				 +  "inner join trader_address AD ON U.user_id = AD.user_id "  
				 +  "inner join address A on A.address_id = AD.address_id "  
				 +  "where a.zone_id=?1 and a.city_id=?2 " , nativeQuery = true)
	List<Trader> queryByParametersCityZone(@Param("zone") long zona, @Param("city") long ciudad);

	

	/*Queries*/
	@Query(value= "select * from dbpayless.usser u"+ 
				  " inner join dbpayless.trader t on u.user_id=t.user_id"+
				  "	where u.name like %:name% ", nativeQuery = true)
	Trader queryFindByUserName(@Param("name") String name);
	
}
