package com.payless.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Zone {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="NAME_ZONE",nullable=false)
	private String name;


	/*MAPEO BIDIRECCIONAL DE MUCHOS */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	@JsonBackReference//esto evita problemas en el json ver el ref en trader
	private City citi;
	
	
	public Zone(){ 	} 
	
	
	public Zone(String name) {
		super();
		this.name = name;
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


	public City getCiti() {
		return citi;
	}


	public void setCiti(City citi) {
		this.citi = citi;
	}
	
	
	
	
}
