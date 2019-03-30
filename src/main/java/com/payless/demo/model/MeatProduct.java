package com.payless.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
public class MeatProduct extends Product{

	//esta columna podria ser u enum, hay que verlo
	@Column(name="ANIMAL_TYPE",nullable=false)
	private String typeAnimal;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_EXPIRY",nullable=false)
	private Date dateExpiry;

	
	public MeatProduct(){}

	public MeatProduct(String _code, String _desc, String _name,  float _price ,String typeAnimal, Date dateExpiry) {
		super( _code, _desc,  _name,    _price);
		this.typeAnimal = typeAnimal;
		this.dateExpiry = dateExpiry;
	}
	
	public String getTypeAnimal() {
		return typeAnimal;
	}

	public void setTypeAnimal(String typeAnimal) {
		this.typeAnimal = typeAnimal;
	}

	public Date getDateExpiry() {
		return dateExpiry;
	}

	public void setDateExpiry(Date dateExpiry) {
		this.dateExpiry = dateExpiry;
	}
	
	
	
	
	
}
