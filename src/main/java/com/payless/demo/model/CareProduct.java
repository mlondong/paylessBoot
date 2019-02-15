package com.payless.demo.model;

import javax.persistence.Entity;

@Entity
public class CareProduct extends Product{

	public CareProduct(){}

	public CareProduct(String _code, String _desc, String _name, float _price) {
		super(_code, _desc, _name, _price);
		// TODO Auto-generated constructor stub
	}

	
}
