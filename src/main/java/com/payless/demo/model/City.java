package com.payless.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CITY_ID",nullable=false,updatable=false)
	private long id;

	@Column(name="NAME_CITY",nullable=false)
	private String name;
	

	@OneToMany(mappedBy="city", 
			 fetch=FetchType.LAZY,
			 cascade = CascadeType.ALL, 
			 orphanRemoval = true)
	@JsonManagedReference
	private List<Zone> zone =  new ArrayList<Zone>();


	
	
	
	public City() {	}
	
	public City(String name) {
		this.name = name;
	}
	
	public void addZone(Zone zone){
		this.zone.add(zone);
	}
	
	public void removeZone(Zone zone){
		this.zone.remove(zone);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Zone> getZone() {
		return zone;
	}

	public void setZone(List<Zone> zone) {
		this.zone = zone;
	}

	

}
