package com.payless.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author root
 * ENTIDAD INVOICE DENTRO DE INVOICE HAY MUCHOS PRODUCTOS
 * UN TRADER PUEDE TENER MUCHOS INVOICES
 * INVOICE MAPEA A MUCHOS PRODUCTS CON UNA TABLA INTERMEDIA INVOICE_PRODUCTS
 * A SU VEZ INVOICE ES MAEADO POR TRADER CON UNA TUN TRADER TIENE MUCHOS NVOICES
 * TABLA INTERNEDIA TRADER_INVOICES
 * 
 */

@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="INVOICE_ID",nullable=false, unique=true)
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_INVOICE",nullable=false )
	private Date dateInvoice;


	/*MAPEO BIDIRECCIONAL DE MUCHOS INVOICES A 1 TRADER*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID_TRADER")
	@JsonBackReference//esto evita problemas en el json ver el ref en trader
	private Trader trader;

	
	/*MAPEO BIDIRECCIONAL DE MUCHOS INVOICES A 1 CONSUMER*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID_CONSUMER")
	@JsonBackReference//esto evita problemas en el json ver el ref en trader
	private Consumer consumer;

	
	
	/*MAPEO BIDIRECCIONAL HACIA PRODUCTOS DADO QUE MUCHOS INVOICES PUEDEN 
	 * TENER MUCHOS PRODCTOS Y MUCHOS PRODUCTOS PUEDEN ESTAR EN MCUHOS INVOICES DE DIFERENTES TRADER*/
	@OneToMany(mappedBy="invoice",cascade= CascadeType.ALL, orphanRemoval=true)
	@JsonManagedReference
	private Collection<InvoiceProduct> products;
	
	
	@Column(name="NUM_INVOICE", updatable=false, nullable=false, unique=true)
	private long numInvoice;
	
	
	
	
	public Invoice(){ }	
	
	public Invoice(Trader _trader, Consumer _consumer){
		this.trader=_trader;
		this.consumer=_consumer;
		this.dateInvoice=new Date();
		this.products= new ArrayList<InvoiceProduct>();
		this.numInvoice=this.generateNumInvoice();
	}
	

	public InvoiceProduct getInvoiceProductWithProduct(long idProduct){
		Collection <InvoiceProduct> invoiceProducts = this.getProducts();
		InvoiceProduct invp = null;
		
		for(InvoiceProduct ip : invoiceProducts){
				if(ip.getPoduct().getId()==idProduct){
					invp=ip;
				}
		}
		return invp;
	}	
	
	
	
	
	/************************************************************************************************************************************/
	
	/* DESDE ACA SE van ADICIONANDO OBJETOS InvoiceProduct A LOS CUALES SE LES AGREGA PRODUCTOS
	 * ES DECIR CADA invoideProducts COMO ENTIDAD CONTIENE UN INVOICE+PRODUCTO+CANTIDAD 
	 * SACADO DEL EJEMPLO DE HIBERNATE*
	 * https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/*/
	
	public void addInvoiceProduct(Product p, int quantity){
		InvoiceProduct invoideProducts = new InvoiceProduct(this, p, quantity);
		products.add(invoideProducts);
	}
	

	public void removeInvoiceProduct(InvoiceProduct ivp){
		 this.getProducts().remove(ivp);
	}

	
	
	
	public long generateNumInvoice(){
		int max=10000;
		int min=1;
		return (long) (Math.random() * ( max - min ));
	}

	
			
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Date getDateInvoice() {
		return dateInvoice;
	}


	public void setDateInvoice(Date dateInvoice) {
		this.dateInvoice = dateInvoice;
	}


	public Trader getTrader() {
		return trader;
	}


	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public Collection<InvoiceProduct> getProducts() {
		return products;
	}

	public void setProducts(Collection<InvoiceProduct> products) {
		this.products = products;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public long getNumInvoice() {
		return numInvoice;
	}

	public void setNumInvoice(long numInvoice) {
		this.numInvoice = numInvoice;
	}

	
		



	
	
}



