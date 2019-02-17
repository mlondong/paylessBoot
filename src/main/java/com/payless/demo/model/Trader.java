package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Trader extends Usser {

	@Column(name="CUIT",nullable=false,updatable=true)
	private long cuit;


	@Column(name="SCORE",nullable=false,updatable=true)
	private int score=0;


	/*SE CREA UN SOLO STOCK POR TRADER Y SE AGREGAR PRODUCTOS AL STOCK*/
	@OneToOne(mappedBy="trader",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	@JsonManagedReference//esto evita mal formacion en el json como es el ref de stock en trader
	private Stock stock;


	/*MAPEO A MUCHAS DIRECCIONES*/
	@Column(name="USER_ID")
	@ElementCollection
	private List<Address> address = new ArrayList<Address>();

	
	/*MAPEO BIDIRECCIONAL DE 1 TRADER A MUCHOS INVOICES*/
	@OneToMany(mappedBy="trader", 
			 fetch=FetchType.LAZY,
			 cascade = CascadeType.ALL, 
			 orphanRemoval = true)
	@JsonManagedReference//esto evita mal formacion en el json como es el ref de stock en trader
	private Collection<Invoice> invoice = new ArrayList<>();
	
	
	
	
	
	/*******************************************************************************************************************************************/	
	public Trader(){}

	public Trader(String _name, String _pass, long _cuit, List<Address> _address){
		super(_name, _pass);
		this.address=_address;
		this.cuit=_cuit;
	}

	public Trader(String _name, String _pass, long _cuit){
		super(_name, _pass);
		this.cuit=_cuit;
	}

	/*************************************************************************************************************************************/	

	/*METODOS ADD REMOVE Y CREAATE STOCK EN TRADER*/
	public void createStock(){
		this.stock = new  Stock();
		this.stock.setDateStock(new Date());
		this.stock.setTrader(this);
	}

	public void addStockProducts(Product p, int quantity){
		this.stock.addProduct(p, quantity);
	}

	public void removeStockProducts(Product p){
		this.stock.removeProduct(p);
	}

	
	
	
	/*METODOS PARA ADICIONAR ASDRESS A TRADER*/

	public void addAddress(Address ad){
		this.address.add(ad);
	}

	public void removeAddress(Address ad){
		this.address.remove(ad);
	}



	/*METODOS PARA ADICIONAR INVOICES*/
	public void addInvoice(Invoice i){
		this.invoice.add(i);
	}
	
	public void removeInvoice(Invoice i){
		this.invoice.remove(i);
	}
	



	/*************************************************************************************************************************************/
	/*METODOS GETTER AND SETER*/

	public Collection<Invoice> getInvoice() {
		return invoice;
	}

	public void setInvoice(Collection<Invoice> invoice) {
		this.invoice = invoice;
	}

	public long getCuit() {
		return cuit;
	}
	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public void setCuit(long cuit) {
		this.cuit = cuit;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}







}
