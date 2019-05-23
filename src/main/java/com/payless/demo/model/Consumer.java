package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Consumer extends Usser {

	@Column(name="DNI",nullable=false,unique=true)
	private long dni;

    @Size(min=5, max=30)
	@Column(name="FIRSTNAME",nullable=false)
	private String firstName;

    @Size(min=5, max=30)
    @Column(name="LASTNAME",nullable=false)
	private String lastName;


	/*MAPEO BIDIRECCIONAL DE 1 CONSUMER A MUCHOS PURCHASE MAPPEDBY CONSUMER DESDE PURCHASE*/
	@OneToMany(mappedBy="consumer", 
			fetch=FetchType.LAZY,
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	@JsonManagedReference
	private Collection<Invoice> invoices = new ArrayList<>();


	/*MAPEO DE ADDRESS ONE TO ONE CONSUMER-ADDRESS*/
	@Embedded
	private Address address;

	

	public Consumer(){}

	public Consumer(String _name, String _pass, long _dni, String _firstName, String _lastName, String _street, int _city, int _zone   ){
		super(_name, _pass);
		this.dni=_dni;
		this.firstName=_firstName;
		this.lastName=_lastName;
		this.address = new Address( _street,  _city, _zone);
		
	}

	
	public Optional<Invoice> findInvoiceByNumber(Long numInvoice){
		return this.invoices.stream().filter(invoice-> numInvoice.equals(invoice.getNumInvoice()) ).findFirst();
	}

	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	
	public long getDni() {
		return dni;
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


	
	public Collection<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Collection<Invoice> invoices) {
		this.invoices = invoices;
	}

	public void addInvoice(Invoice p){
		this.invoices.add(p);	
	}
	
	public void removeInvoice(Invoice p){
		this.invoices.remove(p);	
	}

	@Override
	public String toString() {
		return "Consumer [dni=" + dni + ", firstName=" + firstName + ", lastName=" + lastName + ", invoices=" + invoices
				+ ", address=" + address + "]";
	}
	













}
