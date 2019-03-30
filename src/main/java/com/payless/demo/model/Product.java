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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="PRODUCT_ID",nullable=false,unique=true)
	private long id;

	@Column(name="CODE",nullable=false, unique=true)
	private String code;

	@Column(name="DESCRIPTION",nullable=false)
	private String description;

	@Column(name="PRODUCER",nullable=false)
	private String producer;

	@Column(name="PRICE_UNIT",nullable=false)
	private float priceUnit;

	@Column(name="STATE",nullable=false)
	private boolean state=true; 


	public Product(){}


	public Product(String _code, String _desc, String _producer,  float _price){
		this.code=_code;
		this.description=_desc;
		this.producer=_producer;
		this.priceUnit=_price;

	}


	/***************************************************************************************************************************************/	
	/*METODOS GETTER AND SETTER*/


	public long getId() {
		return id;
	}
	

	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public float getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(float priceUnit) {
		this.priceUnit = priceUnit;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isProduct(){
		return this.state;
	}


	public String getProducer() {
		return producer;
	}


	public void setProducer(String producer) {
		this.producer = producer;
	}


}
