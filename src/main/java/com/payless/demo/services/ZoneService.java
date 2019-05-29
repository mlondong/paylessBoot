package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import com.payless.demo.model.Zone;

public interface ZoneService {

	Optional<Zone> findById(Long arg0);

	List<Zone> findAll();
	List <Zone> findAllZonesByIdCity(Long idCity); 
}
