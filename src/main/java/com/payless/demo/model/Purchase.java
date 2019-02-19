package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Purchase {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PURCHASE_ID")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_CREATION")
	private Date dateCreation;

	@Column(name="NUM_INVOICE")
	private long numInvoice;

	
	/*MAPEO DE MUCHOS PURCHASE HAVE ONE CONSUMER, con la VARIABLE CONSUMER MAPPEDBY="CONSUMER" DESDE CONSUMER*/
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "USER_ID")	
	@JsonBackReference
	private Consumer consumer;
	
	
	
	
	/*MAPEO DE PURCHASE A PRODUCT A TRAVES DE PURCHASEPRODUCT Y PURCHASEID*/
	@OneToMany(mappedBy="purchase",  cascade=CascadeType.ALL,  orphanRemoval=true)
	private Collection<PurchaseProduct> purchaseproduct; 
	


		
	
	
	public Purchase(){
		this.dateCreation= new Date();
		this.purchaseproduct = new ArrayList<PurchaseProduct>();
		this.generateNumInvoice();
	}


	
	/***************************************************************************************************************************************/	
	
	public void addProductInPurchaseProduct( Product prod, int q){
		/*SE VAN GENERARNDO PurchaseProduct CON PRODUCTO +CANTIDAD */
		PurchaseProduct p = new PurchaseProduct(this, prod, q);
		this.purchaseproduct.add(p);
	}
	
	
	
	
	
	public void generateNumInvoice(){
		int max=10000;
		int min=1;
		this.numInvoice= (long) (Math.random() * ( max - min ));
	}

	
	
	
	
	
	
	
	
	
	
	/***************************************************************************************************************************************/	
	//SETTER AND GETTERS
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	
	public long getNumInvoice() {
		return numInvoice;
	}

	public void setNumInvoice(long numInvoice) {
		this.numInvoice = numInvoice;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}





	public Collection<PurchaseProduct> getPurchaseproduct() {
		return purchaseproduct;
	}

	public void setPurchaseproduct(Collection<PurchaseProduct> purchaseproduct) {
		this.purchaseproduct = purchaseproduct;
	}



	








}
