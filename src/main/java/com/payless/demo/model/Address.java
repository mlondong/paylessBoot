package com.payless.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Address {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ADDRESS_ID",nullable=false,updatable=false)
	private Long id; 
	
	@NotNull
	@Column(name="DESCRIPTION")
	private String description;
	

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CITY_ID")
	@JsonManagedReference
	private City city;

	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ZONE_ID")
	@JsonManagedReference
	private Zone zone;

	
	public Address(){}
	
	
	public Address(String description, City city, Zone zone) {
		this.description = description;
		this.city = city;
		this.zone=zone;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public City getCity() {
		return city;
	}


	public void setCity(City city) {
		this.city = city;
	}


	public Zone getZone() {
		return zone;
	}


	public void setZone(Zone zone) {
		this.zone = zone;
	}

	
	

	

	
	
	
	
	
}
