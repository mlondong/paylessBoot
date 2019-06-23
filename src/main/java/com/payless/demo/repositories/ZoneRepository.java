package com.payless.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.Zone;

@Repository
@Transactional
public interface ZoneRepository extends CrudRepository<Zone, Long>{

	
	Optional<Zone> findById(long arg0);
	List<Zone> findAll();
	
	@Query("Select z from Zone z where z.city.id=:idCity order by z.name")
	List <Zone> findAllZonesByIdCity(@Param("idCity") long idCity); 


}
