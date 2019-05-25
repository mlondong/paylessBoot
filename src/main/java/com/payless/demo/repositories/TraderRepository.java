package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Address;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Stock;
import com.payless.demo.model.Trader;




@Repository
@Transactional
public interface TraderRepository extends BaseUserRepository<Trader>{

	Trader findByCuit(long cuit);
	List<Trader> findByStock(Stock stock);
	Optional<Trader> findByName(String name);
	
/*	@Query(value = " SELECT * FROM dbpayless.trader T " +  
				   " INNER JOIN dbpayless.trader_address A ON T.user_id = A.trader_user_id " +  
				   " WHERE A.zone=1 and A.city_id=1 ", nativeQuery = true)*/
	
	@Query(value = " Select * FROM dbpayless.trader t "
				 +  " inner join dbpayless.usser u ON U.user_id = t.user_id "
				 +  " inner join dbpayless.trader_address ad ON U.user_id = AD.trader_user_id "  
				 +  " where  AD.zone=:zone and AD.city_id=:city ",  nativeQuery = true)
	List<Trader> queryByParametersCityZone(@Param("zone") int zone, @Param("city") int city);

	

	/*Queries*/
	@Query(value= "select * from dbpayless.usser u"+ 
				  " inner join dbpayless.trader t on u.user_id=t.user_id"+
				  "	where u.name like %:name% ", nativeQuery = true)
	Trader queryFindByUserName(@Param("name") String name);
	
}
