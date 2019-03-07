package com.payless.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payless.demo.model.City;
import com.payless.demo.model.Zone;

@Repository
@Transactional
public interface ZoneRepository extends CrudRepository<Zone, Long>{

	List<Zone> findAll();
	
	/*@Query("Select id, name_zone FROM zone z where z.city_id=?1")
	Zone findZoneById(long idCity); 
	*/
}
