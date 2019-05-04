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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Trader extends Usser {
	
	@Column(name="CUIT",nullable=false, updatable=true, unique=true)
	private long cuit;

    @Size(min=5, max=30)
	@Column(name="NAME_ENTERPRISE",nullable=false, updatable=true)
	private String nameEnterprise;

	
    @NotNull
    @Email
    @Column(name="EMAIL",nullable=false, updatable=true)
	private String email;

	@Max(5)
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

	
	/*METODOS ADD REMOVE Y CREAATE STOCK EN TRADER*/
	public void createStock(){
		this.stock = new  Stock();
		this.stock.setDateStock(new Date());
		this.stock.setTrader(this);
	}

	public void addStockProducts(Product p, int quantity, int salesprice){
		this.stock.addProduct(p, quantity, salesprice);
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

	
	public String getNameEnterprise() {
		return nameEnterprise;
	}

	public void setNameEnterprise(String nameEnterprise) {
		this.nameEnterprise = nameEnterprise;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Trader [cuit=" + cuit + ", score=" + score + ", stock=" + stock + ", address=" + address + ", invoice="
				+ invoice + "]";
	}







}
