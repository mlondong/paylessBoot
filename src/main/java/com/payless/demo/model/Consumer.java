package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Consumer extends Usser {

	@Column(name="DNI",nullable=false,unique=true)
	private long dni;

	@Column(name="FIRSTNAME",nullable=false)
	private String firstName;

	@Column(name="LASTNAME",nullable=false)
	private String lastName;


	/*MAPEO BIDIRECCIONAL DE 1 CONSUMER A MUCHOS PURCHASE MAPPEDBY CONSUMER DESDE PURCHASE*/
	@OneToMany(mappedBy="consumer", 
			fetch=FetchType.LAZY,
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	@JsonManagedReference
	private Collection<Purchase> purchase= new ArrayList<Purchase>();







	/***************************************************************************************************************************************/	

	public Consumer(){}

	public Consumer(String _name, String _pass, long _dni, String _firstName, String _lastName  ){
		super(_name, _pass);
		this.dni=_dni;
		this.firstName=_firstName;
		this.lastName=_lastName;
	}


	/***************************************************************************************************************************************/	
	//ANOTHER OPERATIONS







	/***************************************************************************************************************************************/
	//Metodos Gter and Setter


	public long getDni() {
		return dni;
	}

	public void addPurchase(Purchase p){
		this.purchase.add(p);	
	}
	
	public void removePurchase(Purchase p){
		this.purchase.remove(p);	
	}
	
	public Collection<Purchase> getPurchase() {
		return purchase;
	}

	public void setPurchase(Collection<Purchase> purchase) {
		this.purchase = purchase;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Consumer [dni=" + dni + ", firstName=" + firstName + ", lastName=" + lastName + ", purchase=" + purchase
				+ "]";
	}












}
