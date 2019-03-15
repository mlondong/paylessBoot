package com.payless.demo.model;

import javax.persistence.Entity;

@Entity
public class MilkProduct extends Product {

	
	public MilkProduct(){}
	
	public MilkProduct(String _code, String _desc, String _name, float _price) {
		super(_code, _desc, _name, _price);
	}
	
	public boolean isMilkProduct(){
		return true;
	}
}
