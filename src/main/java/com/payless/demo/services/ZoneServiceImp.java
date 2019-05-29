package com.payless.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payless.demo.model.Zone;
import com.payless.demo.repositories.ZoneRepository;

@Service
public class ZoneServiceImp implements ZoneService {

	@Autowired
	private ZoneRepository zoneRepository;

	@Override
	public List<Zone> findAll() {
		return zoneRepository.findAll();
	}

	@Override
	public List<Zone> findAllZonesByIdCity(Long idCity) {
		return zoneRepository.findAllZonesByIdCity(idCity);
	}

	@Override
	public Optional<Zone> findById(Long arg0) {
		return zoneRepository.findById(arg0);
	}

	
	
	
}
