package com.payless.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CITY_ID",nullable=false)
	private int city;

	@Column(name="ZONE",nullable=false)
	private int zona;

	
	
	public Address(){}
	
	
	public Address(String description, int city, int zone) {
		this.description = description;
		this.city = city;
		this.zona=zone;
	}

	
	
	/*********************************************************************************************************************************
	 * metodos getter and setter * 
	 */
	
	

	public int getZona() {
		return zona;
	}


	public void setZona(int zona) {
		this.zona = zona;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}


	@Override
	public String toString() {
		return "Address [description=" + description + ", city=" + city + ", zona=" + zona + "]";
	}
	
	
}
