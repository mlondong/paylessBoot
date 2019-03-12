package com.payless.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.City;
import com.payless.demo.model.Consumer;
import com.payless.demo.model.Zone;

@Repository
@Transactional
public interface ZoneRepository extends CrudRepository<Zone, Long>{

	List<Zone> findAll();
	
	@Query("Select z from Zone z where z.citi.id=:idCity")
	List <Zone> findAllZonesByIdCity(@Param("idCity") long idCity); 


}
